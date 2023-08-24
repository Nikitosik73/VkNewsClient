package ru.paramonov.vknewsclient.data.repository

import android.app.Application
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
import kotlinx.coroutines.flow.stateIn
import ru.paramonov.vknewsclient.data.mapper.NewsFeedMapper
import ru.paramonov.vknewsclient.data.network.api.ApiFactory
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.PostComment
import ru.paramonov.vknewsclient.domain.StatisticItem
import ru.paramonov.vknewsclient.domain.StatisticType
import ru.paramonov.vknewsclient.extensions.mergeWith

class NewsFeedRepository(
    application: Application
) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

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
    }

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    val allNewsFeed: StateFlow<List<FeedPost>> = loadedAllNewsFeedFLow
        .mergeWith(another = refreshedListFlow)
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    suspend fun loadNextData() {
        neededNextDataEvents.emit(Unit)
    }

    suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    suspend fun changeLikeStatus(feedPost: FeedPost) {
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

    suspend fun loadComments(feedPost: FeedPost): List<PostComment> {
        delay(2000)
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        return mapper.mapResponseToPostComments(response = response)
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token is null")
    }
}