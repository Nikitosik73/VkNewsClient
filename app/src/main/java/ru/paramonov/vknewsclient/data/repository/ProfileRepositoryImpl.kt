package ru.paramonov.vknewsclient.data.repository

import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import ru.paramonov.vknewsclient.data.mapper.ProfileMapper
import ru.paramonov.vknewsclient.data.network.api.ApiService
import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.entity.WallPost
import ru.paramonov.vknewsclient.domain.repository.ProfileRepository
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: ProfileMapper,
    private val storage: VKPreferencesKeyValueStorage
): ProfileRepository {
    private val token
        get() = VKAccessToken.restore(storage)

    private val scope = CoroutineScope(Dispatchers.Default)

    private val loadProfileInfo: Flow<Profile> = flow {
        delay(2000)
        val response = apiService.getProfileInfo(
            token = getAccessToken()
        )
        val profile = mapper.mapResponseToProfile(response = response)
        emit(profile)
    }.retry {
        delay(RETRY_TIMEOUT_MILLIS)
        true
    }

    override fun getProfileInfo(): Flow<Profile> = loadProfileInfo
        .shareIn(
            scope = scope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    private val _wallPosts = mutableListOf<WallPost>()
    private val wallPost
        get() = _wallPosts.toList()

    private val loadWallPostsFlow: StateFlow<List<WallPost>> = flow {
        val response = apiService.getWallPosts(
            token = getAccessToken()
        )
        val posts = mapper.mapResponseToWallPosts(response = response)
        _wallPosts.addAll(posts)
        emit(wallPost)
    }.stateIn(
        scope = scope,
        started = SharingStarted.Lazily,
        initialValue = wallPost
    )

    override fun getWallPosts(): StateFlow<List<WallPost>> = loadWallPostsFlow

    private fun getAccessToken(): String {
        return token?.accessToken ?: throw IllegalStateException("Token is null")
    }

    companion object {

        private const val RETRY_TIMEOUT_MILLIS = 3000L
    }
}