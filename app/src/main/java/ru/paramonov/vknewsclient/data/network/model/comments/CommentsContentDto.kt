package ru.paramonov.vknewsclient.data.network.model.comments

import com.google.gson.annotations.SerializedName

data class CommentsContentDto(
    @SerializedName("items") val comments: List<CommentsDto>,
    @SerializedName("profiles") val profiles: List<ProfilesDto>
)