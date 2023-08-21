package ru.paramonov.vknewsclient.data.network.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsDto(
    @SerializedName("id") val id: Long,
    @SerializedName("from_id") val fromId: Long,
    @SerializedName("date") val date: Long,
    @SerializedName("text") val text: String
)