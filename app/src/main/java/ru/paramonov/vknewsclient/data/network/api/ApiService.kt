package ru.paramonov.vknewsclient.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.paramonov.vknewsclient.data.network.model.NewsFeedResponseDto
import ru.paramonov.vknewsclient.data.network.model.likes.LikesCountResponseDto

interface ApiService {

    @GET("newsfeed.get?v=5.131")
    suspend fun getAllPosts(
        @Query(ACCESS_TOKEN) token: String,
        @Query(FILTERS) post: String = "post"
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.131")
    suspend fun addLike(
        @Query(ACCESS_TOKEN) token: String,
        @Query(TYPE) type: String = "post",
        @Query(OWNER_ID) ownerId: Long,
        @Query(ITEM_ID) itemId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.131")
    suspend fun deleteLike(
        @Query(ACCESS_TOKEN) token: String,
        @Query(TYPE) type: String = "post",
        @Query(OWNER_ID) ownerId: Long,
        @Query(ITEM_ID) itemId: Long
    ): LikesCountResponseDto

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val FILTERS = "filters"
        private const val OWNER_ID = "owner_id"
        private const val ITEM_ID = "item_id"
        private const val TYPE = "type"
    }
}