package ru.paramonov.vknewsclient.data.network.model.profile.post

import com.google.gson.annotations.SerializedName
import ru.paramonov.vknewsclient.data.network.model.AttachmentDto
import ru.paramonov.vknewsclient.data.network.model.CommentsDto
import ru.paramonov.vknewsclient.data.network.model.LikesDto
import ru.paramonov.vknewsclient.data.network.model.RepostsDto
import ru.paramonov.vknewsclient.data.network.model.ViewsDto

data class WallPostDto(
    @SerializedName("id") val id: Long,
    @SerializedName("owner_id") val ownerId: Long,
    @SerializedName("text") val text: String,
    @SerializedName("date") val date: Long,
    @SerializedName("views") val views: ViewsDto,
    @SerializedName("reposts") val reposts: RepostsDto,
    @SerializedName("comments") val comments: CommentsDto,
    @SerializedName("likes") val likes: LikesDto,
    @SerializedName("attachments") val attachments: List<AttachmentDto>?
)
