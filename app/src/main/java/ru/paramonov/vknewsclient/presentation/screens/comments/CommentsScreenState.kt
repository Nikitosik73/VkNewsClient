package ru.paramonov.vknewsclient.presentation.screens.comments

import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment

sealed class CommentsScreenState {

    object Initial : CommentsScreenState()

    object Loading : CommentsScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): CommentsScreenState()
}