package ru.paramonov.vknewsclient.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.paramonov.vknewsclient.di.annotation.ViewModelKey
import ru.paramonov.vknewsclient.presentation.screens.comments.CommentsViewModel

@Module
interface CommentsViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(
        impl: CommentsViewModel
    ): ViewModel
}
