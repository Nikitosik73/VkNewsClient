package ru.paramonov.vknewsclient.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.paramonov.vknewsclient.domain.entity.FeedPost

interface FavoriteRepository {

    fun getAllFavoriteNewsFeed(): Flow<List<FeedPost>>

    suspend fun addToFavorites(feedPost: FeedPost)

    suspend fun removeFromFavorites(feedPost: FeedPost)
}