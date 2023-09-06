package ru.paramonov.vknewsclient.presentation.screens.favorite

import ru.paramonov.vknewsclient.domain.entity.FeedPost

sealed class FavoriteViewState {

    object Initial : FavoriteViewState()

    object Loading : FavoriteViewState()

    data class FavoriteContent(val content: List<FeedPost>) : FavoriteViewState()
}