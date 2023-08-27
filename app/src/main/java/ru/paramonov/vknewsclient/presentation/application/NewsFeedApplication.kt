package ru.paramonov.vknewsclient.presentation.application

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import ru.paramonov.vknewsclient.di.ApplicationComponent
import ru.paramonov.vknewsclient.di.DaggerApplicationComponent

class NewsFeedApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(context = this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    Log.d("test_recomposition", "getApplicationComponent")
    return (LocalContext.current.applicationContext as NewsFeedApplication).component
}