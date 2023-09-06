package ru.paramonov.vknewsclient.di.module

import android.content.Context
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.paramonov.vknewsclient.data.database.AppDatabase
import ru.paramonov.vknewsclient.data.database.dao.NewsFeedDao
import ru.paramonov.vknewsclient.data.repository.FavoriteRepositoryImpl
import ru.paramonov.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.paramonov.vknewsclient.data.repository.ProfileRepositoryImpl
import ru.paramonov.vknewsclient.di.annotation.ApplicationScope
import ru.paramonov.vknewsclient.domain.repository.FavoriteRepository
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

    @Binds
    @ApplicationScope
    fun bindFavoriteRepository(
        impl: FavoriteRepositoryImpl
    ): FavoriteRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideVkStorage(
            context: Context
        ): VKPreferencesKeyValueStorage =
            VKPreferencesKeyValueStorage(context = context)

        @Provides
        @ApplicationScope
        fun provideDatabase(
            context: Context
        ): AppDatabase = AppDatabase.getInstance(context = context)

        @Provides
        @ApplicationScope
        fun provideDao(
            database: AppDatabase
        ): NewsFeedDao = database.newsFeedDao()
    }
}
