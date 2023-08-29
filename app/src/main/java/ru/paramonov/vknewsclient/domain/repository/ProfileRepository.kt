package ru.paramonov.vknewsclient.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.entity.WallPost

interface ProfileRepository {

    fun getProfileInfo(): Flow<Profile>

    fun getWallPosts(): StateFlow<List<WallPost>>

    suspend fun changeLikesStatus(wallPost: WallPost)
}