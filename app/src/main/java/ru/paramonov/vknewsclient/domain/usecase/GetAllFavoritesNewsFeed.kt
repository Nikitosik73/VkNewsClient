package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetAllFavoritesNewsFeed @Inject constructor(
    private val repository: FavoriteRepository
) {

    operator fun invoke() = repository.getAllFavoriteNewsFeed()
}