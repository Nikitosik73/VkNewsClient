package ru.paramonov.vknewsclient.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.domain.entity.WallPost
import ru.paramonov.vknewsclient.domain.usecase.ChangeLikeStatusWallPostUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetProfileInfoUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetWallPostsUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getWallPostsUseCase: GetWallPostsUseCase,
    private val changeLikeStatusWallPostUseCase: ChangeLikeStatusWallPostUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val viewState: Flow<ProfileViewState> = getProfileInfoUseCase()
        .flatMapLatest { profile ->
            getWallPostsUseCase()
                .map { wallPosts ->
                    ProfileViewState.ProfileContent(
                        profile = profile,
                        wallPosts = wallPosts
                    ) as ProfileViewState
                }
        }.onStart { emit(ProfileViewState.Loading) }

    fun changeLikeStatus(wallPost: WallPost) {
        viewModelScope.launch {
            changeLikeStatusWallPostUseCase(wallPost = wallPost)
        }
    }
}
