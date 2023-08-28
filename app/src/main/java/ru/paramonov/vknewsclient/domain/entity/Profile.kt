package ru.paramonov.vknewsclient.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class Profile(
    val id: Long,
    val fullName: String,
    val screenName: String,
    val profilePhoto: String,
    val homeTown: String,
    val bDate: String
)