package ru.paramonov.vknewsclient.presentation.screens.profile

import ru.paramonov.vknewsclient.domain.entity.Profile

sealed class ProfileViewState {

    object Initial : ProfileViewState()

    object Loading : ProfileViewState()

    data class ProfileContent(
        val profile: Profile
    ) : ProfileViewState()
}