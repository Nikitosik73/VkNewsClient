package ru.paramonov.vknewsclient.data.network.model

import com.google.gson.annotations.SerializedName

data class ViewsDto(
    @SerializedName("count") val count: Int
)