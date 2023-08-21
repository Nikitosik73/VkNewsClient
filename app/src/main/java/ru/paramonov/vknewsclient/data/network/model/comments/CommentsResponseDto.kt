package ru.paramonov.vknewsclient.data.network.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsResponseDto(
    @SerializedName("response") val commentsContent: CommentsContentDto
)