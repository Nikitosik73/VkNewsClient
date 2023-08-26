package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class DeletePostUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(feedPost: FeedPost) = repository.deletePost(feedPost = feedPost)
}