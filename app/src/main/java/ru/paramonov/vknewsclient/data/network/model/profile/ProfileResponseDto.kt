package ru.paramonov.vknewsclient.data.network.model.profile

import com.google.gson.annotations.SerializedName

data class ProfileResponseDto(
    @SerializedName("response") val profileContent: ProfileContentDto
)