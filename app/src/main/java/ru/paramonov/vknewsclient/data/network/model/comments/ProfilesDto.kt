package ru.paramonov.vknewsclient.data.network.model.comments

import com.google.gson.annotations.SerializedName

data class ProfilesDto(
    @SerializedName("id") val id: Long,
    @SerializedName("photo_200") val photoUrl: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)