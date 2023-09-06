package ru.paramonov.vknewsclient.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.paramonov.vknewsclient.di.annotation.ViewModelKey
import ru.paramonov.vknewsclient.presentation.screens.favorite.FavoriteViewModel
import ru.paramonov.vknewsclient.presentation.screens.login.AuthViewModel
import ru.paramonov.vknewsclient.presentation.screens.newsfeed.NewsFeedViewModel
import ru.paramonov.vknewsclient.presentation.screens.profile.ProfileViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(
        impl: AuthViewModel
    ): ViewModel

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(
        impl: NewsFeedViewModel
    ): ViewModel

    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    @Binds
    fun bindProfileViewModel(
        impl: ProfileViewModel
    ): ViewModel

    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    @Binds
    fun bindFavoriteViewModel(
        impl: FavoriteViewModel
    ): ViewModel
}
