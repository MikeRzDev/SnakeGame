package com.mikerzdev.snakegame.di

import com.mikerzdev.snakegame.data.game.GameControllerImpl
import com.mikerzdev.snakegame.domain.game.GameController
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@InstallIn(ViewModelComponent::class)
@Module
class GameModule {
    @ViewModelScoped
    @Provides
    fun provideGameController(localRepository: LocalRepository): GameController {
        return GameControllerImpl(localRepository)
    }
}