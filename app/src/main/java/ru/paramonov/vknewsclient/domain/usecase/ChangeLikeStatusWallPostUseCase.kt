package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.entity.WallPost
import ru.paramonov.vknewsclient.domain.repository.ProfileRepository
import javax.inject.Inject

class ChangeLikeStatusWallPostUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    suspend operator fun invoke(wallPost: WallPost) = repository.changeLikesStatus(wallPost = wallPost)
}