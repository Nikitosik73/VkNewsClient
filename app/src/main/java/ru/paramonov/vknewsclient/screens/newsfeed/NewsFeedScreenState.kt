package ru.paramonov.vknewsclient.screens.newsfeed

import ru.paramonov.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial : NewsFeedScreenState()

    data class Posts(
        val posts: List<FeedPost>
    ) : NewsFeedScreenState()
}