package ru.paramonov.vknewsclient.domain.usecase

import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

    suspend operator fun invoke() = repository.loadNextData()
}