package ru.paramonov.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
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
                val someState = remember { mutableStateOf(true) }
                Log.d("MainActivity", "Recomposition: ${someState.value}")
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
                LaunchedEffect(key1 = Unit) {
                    Log.d("MainActivity", "LaunchedEffect")
                }
                SideEffect {
                    Log.d("MainActivity", "SideEffect")
                }
                Button(
                    onClick = {
                        someState.value = !someState.value
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "change state")
                }
            }
        }
    }
}
