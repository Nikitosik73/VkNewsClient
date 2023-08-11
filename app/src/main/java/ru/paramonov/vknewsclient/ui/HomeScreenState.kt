package ru.paramonov.vknewsclient.ui

import ru.paramonov.vknewsclient.domain.FeedPost
import ru.paramonov.vknewsclient.domain.PostComment

sealed class HomeScreenState {

    object Initial : HomeScreenState()

    data class Posts(
        val posts: List<FeedPost>
    ) : HomeScreenState()

    data class Comments(
        val feedPost: FeedPost,
        val comments: List<PostComment>
    ): HomeScreenState()
}