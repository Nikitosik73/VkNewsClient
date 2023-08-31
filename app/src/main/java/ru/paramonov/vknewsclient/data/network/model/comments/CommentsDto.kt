package ru.paramonov.vknewsclient.data.network.model.comments

import com.google.gson.annotations.SerializedName
import ru.paramonov.vknewsclient.data.network.model.LikesDto

data class CommentsDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val fromId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String,
    @SerializedName("likes") val likes: LikesDto
)