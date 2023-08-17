package ru.paramonov.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import ru.paramonov.vknewsclient.screens.main.MainScreen
import ru.paramonov.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                val authLauncher = rememberLauncherForActivityResult(
                    contract = VK.getVKAuthActivityResultContract(),
                    onResult = { result ->
                        when(result) {
                            is VKAuthenticationResult.Success -> {
                                Log.d("MainActivity", "Success auth")
                            }
                            is VKAuthenticationResult.Failed -> {
                                Log.d("MainActivity", "Failed auth")
                            }
                        }
                    }
                )
                authLauncher.launch(listOf(VKScope.WALL))
                ActivityResultApiTest()
            }
        }
    }
}
