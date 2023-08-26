package ru.paramonov.vknewsclient.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.domain.entity.PostComment
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetCommentsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(feedPost: FeedPost): Flow<List<PostComment>> = repository.getComments(feedPost)
}