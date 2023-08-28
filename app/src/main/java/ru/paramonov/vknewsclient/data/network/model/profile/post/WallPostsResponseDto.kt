package ru.paramonov.vknewsclient.data.network.model.profile.post

import com.google.gson.annotations.SerializedName

data class WallPostsResponseDto(
    @SerializedName("response") val wallPostContent: WallPostContentDto
)