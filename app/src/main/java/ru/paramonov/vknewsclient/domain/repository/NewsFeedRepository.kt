package ru.paramonov.vknewsclient.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment

interface NewsFeedRepository {

    fun getAuthStateFlow(): StateFlow<AuthState>

    fun getAllNewsFeed(): StateFlow<List<FeedPost>>

    fun getComments(feedPost: FeedPost): Flow<List<PostComment>>

    suspend fun loadNextData()

    suspend fun checkAuthState()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changeLikeStatus(feedPost: FeedPost)
}