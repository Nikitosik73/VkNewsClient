package ru.paramonov.vknewsclient.data.network.model.profile.post

import com.google.gson.annotations.SerializedName

data class ProfileDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_100") val photoUrl: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)