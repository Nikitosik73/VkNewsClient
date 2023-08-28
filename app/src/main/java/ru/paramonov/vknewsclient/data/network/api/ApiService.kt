package ru.paramonov.vknewsclient.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.paramonov.vknewsclient.data.network.model.NewsFeedResponseDto
import ru.paramonov.vknewsclient.data.network.model.comments.CommentsResponseDto
import ru.paramonov.vknewsclient.data.network.model.likes.LikesCountResponseDto
import ru.paramonov.vknewsclient.data.network.model.profile.ProfileResponseDto

interface ApiService {

    @GET("newsfeed.get?v=5.131")
    suspend fun getAllPosts(
        @Query(ACCESS_TOKEN) token: String,
        @Query(FILTERS) post: String = TYPE_POST
    ): NewsFeedResponseDto

    @GET("newsfeed.get?v=5.131")
    suspend fun getAllPosts(
        @Query(ACCESS_TOKEN) token: String,
        @Query(FILTERS) post: String = TYPE_POST,
        @Query(START_FROM) startFrom: String
    ): NewsFeedResponseDto

    @GET("likes.add?v=5.131")
    suspend fun addLike(
        @Query(ACCESS_TOKEN) token: String,
        @Query(TYPE) type: String = TYPE_POST,
        @Query(OWNER_ID) ownerId: Long,
        @Query(ITEM_ID) itemId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.131")
    suspend fun deleteLike(
        @Query(ACCESS_TOKEN) token: String,
        @Query(TYPE) type: String = TYPE_POST,
        @Query(OWNER_ID) ownerId: Long,
        @Query(ITEM_ID) itemId: Long
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.131")
    suspend fun ignorePost(
        @Query(ACCESS_TOKEN) token: String,
        @Query(TYPE) type: String = TYPE_WALL,
        @Query(OWNER_ID) ownerId: Long,
        @Query(ITEM_ID) itemId: Long
    )

    @GET("wall.getComments?v=5.131")
    suspend fun getComments(
        @Query(ACCESS_TOKEN) token: String,
        @Query(OWNER_ID) ownerId: Long,
        @Query(POST_ID) postId: Long,
        @Query(EXTENDED) extended: Int = 1,
        @Query(FIELDS) field: String = "photo_200"
    ): CommentsResponseDto

    @GET("account.getProfileInfo?v=5.131")
    suspend fun getProfileInfo(
        @Query(ACCESS_TOKEN) token: String
    ): ProfileResponseDto

    companion object {
        private const val ACCESS_TOKEN = "access_token"
        private const val FILTERS = "filters"
        private const val OWNER_ID = "owner_id"
        private const val ITEM_ID = "item_id"
        private const val TYPE = "type"
        private const val TYPE_POST = "post"
        private const val TYPE_WALL = "wall"
        private const val START_FROM = "start_from"
        private const val POST_ID = "post_id"
        private const val EXTENDED = "extended"
        private const val FIELDS = "fields"
    }
}