package ru.paramonov.vknewsclient.presentation.application

import android.app.Application
import ru.paramonov.vknewsclient.di.DaggerApplicationComponent
import ru.paramonov.vknewsclient.domain.entity.FeedPost

class NewsFeedApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory()
            .create(context = this)
    }
}