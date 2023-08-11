package ru.paramonov.vknewsclient.domain

import ru.paramonov.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val commentText: String = "Long comment text",
    val datePublication: String = "14:00",
    val avatarResId: Int = R.drawable.comment_author_avatar
)
