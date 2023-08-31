package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.entity.PostComment
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ChangeLikesStatusCommentUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke(comment: PostComment, ownerId: Long) =
        repository.changeLikesStatusComment(comment = comment, ownerId = ownerId)
}