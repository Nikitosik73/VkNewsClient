package ru.paramonov.vknewsclient.domain.entity

import androidx.compose.runtime.Immutable

@Immutable
data class WallPost(
    val id: Long,
    val ownerId: Long,
    val communityName: String,
    val datePublication: String,
    val communityImageUrl: String,
    val contentText: String,
    val contentImageUrl: String?,
    val statistics: List<StatisticItem>,
    val isLiked: Boolean
)
