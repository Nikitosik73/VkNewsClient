package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.repository.FavoriteRepository
import javax.inject.Inject

class RemoveFromFavoritesUseCase @Inject constructor(
    private val repository: FavoriteRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) =
        repository.removeFromFavorites(feedPost = feedPost)
}