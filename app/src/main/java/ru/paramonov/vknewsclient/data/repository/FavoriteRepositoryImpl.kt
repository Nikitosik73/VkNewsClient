package ru.paramonov.vknewsclient.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.paramonov.vknewsclient.data.database.dao.NewsFeedDao
import ru.paramonov.vknewsclient.data.mapper.FavoriteMapper
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.repository.FavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val dao: NewsFeedDao,
    private val mapper: FavoriteMapper
) : FavoriteRepository {

    override fun getAllFavoriteNewsFeed(): Flow<List<FeedPost>> {
        val allNewsFeed = dao.getAllNewsFeed()
        return allNewsFeed.map { listDbModels ->
            listDbModels.map { dbModel ->
                mapper.mapDbModelToEntity(db = dbModel)
            }
        }
    }

    override suspend fun addToFavorites(feedPost: FeedPost) {
        val dbModel = mapper.mapEntityToDbModel(entity = feedPost)
        dao.insertFeedPost(feedPostDbModel = dbModel)
    }

    override suspend fun removeFromFavorites(feedPost: FeedPost) {
        dao.removeFeedPost(feedPostId = feedPost.id)
    }
}