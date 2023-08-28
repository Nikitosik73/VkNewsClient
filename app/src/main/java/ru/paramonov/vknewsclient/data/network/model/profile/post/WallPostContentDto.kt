package ru.paramonov.vknewsclient.data.network.model.profile.post

import com.google.gson.annotations.SerializedName

data class WallPostContentDto(
    @SerializedName("items") val posts: List<WallPostDto>,
    @SerializedName("profiles") val profiles: List<ProfileDto>
)