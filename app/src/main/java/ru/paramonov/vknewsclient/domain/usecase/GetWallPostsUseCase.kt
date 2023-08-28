package ru.paramonov.vknewsclient.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.entity.WallPost
import ru.paramonov.vknewsclient.domain.repository.ProfileRepository
import javax.inject.Inject

class GetWallPostsUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    operator fun invoke(): StateFlow<List<WallPost>> = repository.getWallPosts()
}