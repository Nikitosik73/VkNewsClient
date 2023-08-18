package ru.paramonov.vknewsclient.data.network.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.paramonov.vknewsclient.data.network.model.NewsFeedResponseDto

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun getAllRecommendedPosts(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}