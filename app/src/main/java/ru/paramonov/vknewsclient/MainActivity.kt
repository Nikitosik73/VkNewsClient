package ru.paramonov.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import ru.paramonov.vknewsclient.ui.MainScreen
import ru.paramonov.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VkNewsClientTheme {
                TestLazyColumn(viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun TestLazyColumn(viewModel: MainViewModel) {
    LazyColumn {
        item {
            Text(text = "Title", fontSize = 24.sp)
        }
        items(10) {
            InstagramProfileCard(viewModel = viewModel)
        }
        item {
            Image(
                painter = painterResource(id = R.drawable.img),
                contentDescription = null
            )
        }
        items(200) {
            Log.d("test_lazy_column", "number: $it")
            InstagramProfileCard(viewModel = viewModel)
        }
    }
}
