package ru.paramonov.vknewsclient.data.network.model.likes

import com.google.gson.annotations.SerializedName

data class LikesCountResponseDto(
    @SerializedName("response") val likes: LikesCountDto
)