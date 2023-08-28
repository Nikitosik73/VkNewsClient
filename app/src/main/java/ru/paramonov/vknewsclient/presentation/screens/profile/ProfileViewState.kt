package ru.paramonov.vknewsclient.presentation.screens.profile

import ru.paramonov.vknewsclient.domain.entity.Profile
import ru.paramonov.vknewsclient.domain.entity.WallPost

sealed class ProfileViewState {

    object Initial : ProfileViewState()

    object Loading : ProfileViewState()

    data class ProfileContent(
        val profile: Profile,
        val wallPosts: List<WallPost>
    ) : ProfileViewState()
}