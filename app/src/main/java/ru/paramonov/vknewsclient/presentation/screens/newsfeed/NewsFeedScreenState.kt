package ru.paramonov.vknewsclient.presentation.screens.newsfeed

import ru.paramonov.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>,
        val nextDataLoading: Boolean = false
    ) : NewsFeedScreenState()
}