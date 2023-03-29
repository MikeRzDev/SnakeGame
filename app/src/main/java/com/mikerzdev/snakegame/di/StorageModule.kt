package com.mikerzdev.snakegame.di

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.mikerzdev.snakegame.data.repository.LocalRepositoryImpl
import com.mikerzdev.snakegame.data.source.local.DataStoreManager
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class StorageModule {
    @Singleton
    @Provides
    fun dataStore(@ApplicationContext context: Context) =
        preferencesDataStore(name = "data-store").getValue(context, String::javaClass)

    @Singleton
    @Provides
    fun provideLocalRepositoryRepository(dataStoreManager: DataStoreManager): LocalRepository {
        return LocalRepositoryImpl(dataStoreManager)
    }

}