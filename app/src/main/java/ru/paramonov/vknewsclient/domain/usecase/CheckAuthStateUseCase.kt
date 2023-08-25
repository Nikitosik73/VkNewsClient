package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository

class CheckAuthStateUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() = repository.checkAuthState()
}