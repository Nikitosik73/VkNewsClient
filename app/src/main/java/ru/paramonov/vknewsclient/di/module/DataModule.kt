package ru.paramonov.vknewsclient.di.module

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.paramonov.vknewsclient.data.repository.ProfileRepositoryImpl
import ru.paramonov.vknewsclient.di.annotation.ApplicationScope
import ru.paramonov.vknewsclient.domain.repository.NewsFeedRepository
import ru.paramonov.vknewsclient.domain.repository.ProfileRepository

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindNewsFeedRepository(
        impl: NewsFeedRepositoryImpl
    ): NewsFeedRepository

    @Binds
    @ApplicationScope
    fun bindProfileRepository(
        impl: ProfileRepositoryImpl
    ): ProfileRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideVkStorage(
            context: Context
        ): VKPreferencesKeyValueStorage =
            VKPreferencesKeyValueStorage(context = context)
    }
}
