package ru.paramonov.vknewsclient.data.network.model.likes

import com.google.gson.annotations.SerializedName

data class LikesCountDto(
    @SerializedName("likes") val count: Int
)