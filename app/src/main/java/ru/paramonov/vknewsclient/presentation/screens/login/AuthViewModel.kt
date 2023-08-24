package ru.paramonov.vknewsclient.presentation.screens.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthenticationResult

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val _viewState = MutableLiveData<AuthViewState>(AuthViewState.Initial)
    val viewState: LiveData<AuthViewState> = _viewState

    init {
        val storage = VKPreferencesKeyValueStorage(application)
        val token = VKAccessToken.restore(storage)
        val loggedIn = token != null && token.isValid
        _viewState.value = if (loggedIn) AuthViewState.Authorized else AuthViewState.NotAuthorized
    }

    fun performAuthResult(result: VKAuthenticationResult) {
        if (result is VKAuthenticationResult.Success) {
            _viewState.value = AuthViewState.Authorized
        } else {
            _viewState.value = AuthViewState.NotAuthorized
        }
    }
}