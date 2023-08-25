package ru.paramonov.vknewsclient.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository

class GetAllNewsFeedUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>> = repository.getAllNewsFeed()
}