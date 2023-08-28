package ru.paramonov.vknewsclient.presentation.screens.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import ru.paramonov.vknewsclient.domain.usecase.GetProfileInfoUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val getProfileInfoUseCase: GetProfileInfoUseCase
): ViewModel() {

    val viewState: Flow<ProfileViewState> = getProfileInfoUseCase()
        .map { ProfileViewState.ProfileContent(profile = it) as ProfileViewState }
        .onStart { emit(ProfileViewState.Loading) }
}