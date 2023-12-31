package ru.paramonov.vknewsclient.data.repository

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.paramonov.vknewsclient.data.mapper.NewsFeedMapper
import ru.paramonov.vknewsclient.data.network.api.ApiService
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment
import ru.paramonov.vknewsclient.domain.entity.StatisticItem
import ru.paramonov.vknewsclient.domain.entity.StatisticType
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import ru.paramonov.vknewsclient.extensions.mergeWith
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper,
    private val storage: VKPreferencesKeyValueStorage
) : NewsFeedRepository {
    private val token
        get() = VKAccessToken.restore(storage)

    private val scope = CoroutineScope(Dispatchers.Default)

    private val neededNextDataEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedAllNewsFeedFLow = flow {
        neededNextDataEvents.emit(Unit)
        neededNextDataEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.getAllPosts(getAccessToken())
            } else {
                apiService.getAllPosts(token = getAccessToken(), startFrom = startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToNewsFeed(response = response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val checkAuthStateEvents = MutableSharedFlow<Unit>(replay = 1)

    private val authStateFlow: StateFlow<AuthState> = flow {
        checkAuthStateEvents.emit(Unit)
        checkAuthStateEvents.collect {
            val currentToken = token
            val loggedIn = currentToken != null && currentToken.isValid
            val authStata = if (loggedIn) AuthState.Authorized else AuthState.NotAuthorized
            emit(authStata)
        }
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = AuthState.Initial
    )

    override fun getAuthStateFlow(): StateFlow<AuthState> = authStateFlow

    private val allNewsFeed: StateFlow<List<FeedPost>> = loadedAllNewsFeedFLow
        .mergeWith(another = refreshedListFlow)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override fun getAllNewsFeed(): StateFlow<List<FeedPost>> = allNewsFeed

    override suspend fun loadNextData() {
        neededNextDataEvents.emit(Unit)
    }

    override suspend fun checkAuthState() {
        checkAuthStateEvents.emit(Unit)
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun changeLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        }
        val modifiedLikes = response.likes.count
        val modifiedStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = modifiedLikes))
        }
        val modifiedPost = feedPost.copy(
            statistics = modifiedStatistics,
            isLiked = !feedPost.isLiked
        )
        val currentPostIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[currentPostIndex] = modifiedPost
        refreshedListFlow.emit(feedPosts)
    }

    private val _comments = mutableListOf<PostComment>()
    private val comments: List<PostComment>
        get() = _comments.toList()

    private val refreshedListComments = MutableSharedFlow<List<PostComment>>()

    override fun getComments(feedPost: FeedPost): Flow<List<PostComment>> = flow {
        delay(2000)
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        val commentList = mapper.mapResponseToPostComments(response = response)
        _comments.clear()
        _comments.addAll(commentList)
        emit(comments)
    }.mergeWith(another = refreshedListComments).retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    override suspend fun changeLikesStatusComment(comment: PostComment, ownerId: Long) {
        val response = if (comment.isLiked) {
            apiService.deleteLikeComment(
                token = getAccessToken(),
                ownerId = ownerId,
                itemId = comment.id
            )
        } else {
            apiService.addLikeComment(
                token = getAccessToken(),
                ownerId = ownerId,
                itemId = comment.id
            )
        }
        val modifiedLikes = response.likes.count
        val modifiedStatisticsComments = comment.likes.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, count = modifiedLikes))
        }
        val modifiedComment = comment.copy(
            likes = modifiedStatisticsComments,
            isLiked = !comment.isLiked
        )
        val currentCommentIndex = _comments.indexOf(comment)
        _comments[currentCommentIndex] = modifiedComment
        refreshedListComments.emit(comments)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token is null")
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}