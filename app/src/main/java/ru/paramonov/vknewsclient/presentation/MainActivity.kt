package ru.paramonov.vknewsclient.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import ru.paramonov.vknewsclient.presentation.screens.login.AuthViewModel
import ru.paramonov.vknewsclient.domain.entity.AuthState
import ru.paramonov.vknewsclient.presentation.application.NewsFeedApplication
import ru.paramonov.vknewsclient.presentation.screens.login.LoginScreen
import ru.paramonov.vknewsclient.presentation.screens.main.MainScreen
import ru.paramonov.vknewsclient.presentation.ui.theme.VkNewsClientTheme
import ru.paramonov.vknewsclient.presentation.viewmodelfactory.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as NewsFeedApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val viewModel: AuthViewModel = viewModel(factory = viewModelFactory)
                val viewState = viewModel.viewState.collectAsState(AuthState.Initial)

                val authLauncher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                    onResult = { viewModel.performAuthResult() }
                )

                when(viewState.value) {
                    is AuthState.Authorized -> {
                        MainScreen(viewModelFactory = viewModelFactory)
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
