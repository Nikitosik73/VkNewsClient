package ru.paramonov.vknewsclient.presentation.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.paramonov.vknewsclient.domain.usecase.GetProfileInfoUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetWallPostsUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase,
    private val getWallPostsUseCase: GetWallPostsUseCase
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
}
