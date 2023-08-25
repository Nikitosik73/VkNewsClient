package ru.paramonov.vknewsclient.domain.entity

data class PostComment(
    val id: Long,
    val authorName: String,
    val commentText: String,
    val datePublication: String,
    val avatarUrl: String
)
