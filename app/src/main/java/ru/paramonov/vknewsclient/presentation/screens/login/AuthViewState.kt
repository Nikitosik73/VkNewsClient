package ru.paramonov.vknewsclient.presentation.screens.login

sealed class AuthViewState {

    object Initial : AuthViewState()

    object Authorized : AuthViewState()

    object NotAuthorized : AuthViewState()
}
