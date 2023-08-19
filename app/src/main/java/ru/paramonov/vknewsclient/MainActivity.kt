package ru.paramonov.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.paramonov.vknewsclient.presentation.screens.login.AuthViewModel
import ru.paramonov.vknewsclient.presentation.screens.login.AuthViewState
import ru.paramonov.vknewsclient.presentation.screens.login.LoginScreen
import ru.paramonov.vknewsclient.presentation.screens.main.MainScreen
import ru.paramonov.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val viewModel: AuthViewModel = viewModel()
                val viewState = viewModel.viewState.observeAsState(AuthViewState.Initial)

                val authLauncher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                    onResult = { viewModel.performAuthResult(it) }
                )

                when(viewState.value) {
                    is AuthViewState.Authorized -> {
                        MainScreen()
                    }
                    is AuthViewState.NotAuthorized -> {
                        LoginScreen {
                            authLauncher.launch(listOf(VKScope.WALL,VKScope.FRIENDS))
                        }
                    }
                    is AuthViewState.Initial -> {}
                }
            }
        }
    }
}
