package ru.paramonov.vknewsclient.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetAllNewsFeedUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<List<FeedPost>> = repository.getAllNewsFeed()
}