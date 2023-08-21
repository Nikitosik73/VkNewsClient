package ru.paramonov.vknewsclient.domain

data class PostComment(
    val id: Long,
    val authorName: String,
    val commentText: String,
    val datePublication: String,
    val avatarUrl: String
)
