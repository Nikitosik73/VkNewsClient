package ru.paramonov.vknewsclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.presentation.application.getApplicationComponent
import ru.paramonov.vknewsclient.presentation.screens.login.AuthViewModel
import ru.paramonov.vknewsclient.presentation.screens.login.LoginScreen
import ru.paramonov.vknewsclient.presentation.screens.main.MainScreen
import ru.paramonov.vknewsclient.presentation.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val component = getApplicationComponent()
            val viewModel: AuthViewModel = viewModel(factory = component.getViewModelFactory())
            val viewState = viewModel.viewState.collectAsState(AuthState.Initial)

            val authLauncher = rememberLauncherForActivityResult(
                contract = VK.getVKAuthActivityResultContract(),
                onResult = { viewModel.performAuthResult() }
            )
            VkNewsClientTheme {
                when(viewState.value) {
                    is AuthState.Authorized -> {
                        MainScreen()
                    }
                    is AuthState.NotAuthorized -> {
                        LoginScreen {
                            authLauncher.launch(listOf(VKScope.WALL, VKScope.FRIENDS))
                        }
                    }
                    is AuthState.Initial -> {}
                }
            }
        }
    }
}
