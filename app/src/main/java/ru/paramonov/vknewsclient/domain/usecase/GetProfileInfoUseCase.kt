package ru.paramonov.vknewsclient.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.repository.ProfileRepository
import javax.inject.Inject

class GetProfileInfoUseCase @Inject constructor(
    private val repository: ProfileRepository
) {

    operator fun invoke(): Flow<Profile> = repository.getProfileInfo()
}