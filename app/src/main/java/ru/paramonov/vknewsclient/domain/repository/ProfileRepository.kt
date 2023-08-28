package ru.paramonov.vknewsclient.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.paramonov.vknewsclient.domain.entity.Profile

interface ProfileRepository {

    fun getProfileInfo(): Flow<Profile>
}