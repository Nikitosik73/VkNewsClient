package ru.paramonov.vknewsclient.domain.usecase

import kotlinx.coroutines.flow.StateFlow
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository
) {

    operator fun invoke(): StateFlow<AuthState> = repository.getAuthStateFlow()
}