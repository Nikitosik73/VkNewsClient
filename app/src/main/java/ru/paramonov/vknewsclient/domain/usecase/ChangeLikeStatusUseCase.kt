package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository

class ChangeLikeStatusUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) = repository.changeLikeStatus(feedPost = feedPost)
}