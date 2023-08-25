package ru.paramonov.vknewsclient.presentation.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.domain.usecase.CheckAuthStateUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetAuthStateUseCase

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = NewsFeedRepositoryImpl(application)

    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    private val checkAuthStateUseCase = CheckAuthStateUseCase(repository)

    val viewState: Flow<AuthState> = getAuthStateUseCase()

    fun performAuthResult() = viewModelScope.launch {
        checkAuthStateUseCase()
    }
}