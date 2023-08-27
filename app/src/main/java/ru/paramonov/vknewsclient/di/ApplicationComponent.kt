package ru.paramonov.vknewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.paramonov.vknewsclient.di.annotation.ApplicationScope
import ru.paramonov.vknewsclient.di.module.DataModule
import ru.paramonov.vknewsclient.di.module.NetworkModule
import ru.paramonov.vknewsclient.di.module.ViewModelModule
import ru.paramonov.vknewsclient.presentation.viewmodelfactory.ViewModelFactory

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory
    
    fun getCommentsScreenFactory(): CommentsScreenSubcomponent.Factory

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}