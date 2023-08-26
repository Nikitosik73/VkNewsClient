package ru.paramonov.vknewsclient.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.domain.usecase.CheckAuthStateUseCase
import ru.paramonov.vknewsclient.domain.usecase.GetAuthStateUseCase
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val checkAuthStateUseCase: CheckAuthStateUseCase
) : ViewModel() {

    val viewState: Flow<AuthState> = getAuthStateUseCase()

    fun performAuthResult() = viewModelScope.launch {
        checkAuthStateUseCase()
    }
}