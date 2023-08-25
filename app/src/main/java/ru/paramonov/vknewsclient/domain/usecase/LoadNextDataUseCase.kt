package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository

class LoadNextDataUseCase(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() = repository.loadNextData()
}