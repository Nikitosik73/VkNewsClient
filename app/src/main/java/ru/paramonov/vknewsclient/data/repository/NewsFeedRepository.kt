package ru.paramonov.vknewsclient.data.repository

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import ru.paramonov.vknewsclient.data.mapper.NewsFeedMapper
import ru.paramonov.vknewsclient.data.network.api.ApiFactory
import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.StatisticItem
import ru.paramonov.vknewsclient.domain.StatisticType

class NewsFeedRepository(
    application: Application
) {

    private val storage = VKPreferencesKeyValueStorage(application)
    private val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPost: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadAllNewsFeed(): List<FeedPost> {
        val response = apiService.getAllPosts(getAccessToken())
        val posts = mapper.mapResponseToNewsFeed(response = response)
        _feedPosts.addAll(posts)
        return posts
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
    }

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("token is null")
    }
}