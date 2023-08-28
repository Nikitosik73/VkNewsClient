package ru.paramonov.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileContentDto(
    @SerializedName("id") val id: Long,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
    @SerializedName("screen_name") val accountName: String,
    @SerializedName("photo_200") val profilePhoto: String,
    @SerializedName("home_town") val homeTown: String,
    @SerializedName("bdate") val bDate: String
)
