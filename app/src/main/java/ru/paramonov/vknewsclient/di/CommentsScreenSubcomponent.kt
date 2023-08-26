package ru.paramonov.vknewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.paramonov.vknewsclient.di.module.CommentsViewModelModule
import ru.paramonov.vknewsclient.domain.entity.FeedPost
import ru.paramonov.vknewsclient.presentation.viewmodelfactory.ViewModelFactory

@Subcomponent(
    modules = [
        CommentsViewModelModule::class
    ]
)
interface CommentsScreenSubcomponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenSubcomponent
    }
}
