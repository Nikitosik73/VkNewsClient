package ru.paramonov.vknewsclient.screens.login

sealed class AuthViewState {

    object Initial : AuthViewState()

    object Authorized : AuthViewState()

    object NotAuthorized : AuthViewState()
}
