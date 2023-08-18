package ru.paramonov.vknewsclient.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult

class AuthViewModel : ViewModel() {

    private val _viewState = MutableLiveData<AuthViewState>(AuthViewState.Initial)
    val viewState: LiveData<AuthViewState> = _viewState

    init {
        _viewState.value = if (VK.isLoggedIn()) AuthViewState.Authorized else AuthViewState.NotAuthorized
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _viewState.value = AuthViewState.Authorized
        } else {
            _viewState.value = AuthViewState.NotAuthorized
        }
    }
}