package com.mikerzdev.snakegame.domain.game

import com.mikerzdev.snakegame.domain.model.game.GameRenderData
import com.mikerzdev.snakegame.domain.model.game.SoundEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

abstract class GameController {
    abstract val graphicsState: Flow<GameRenderData>
    abstract val soundState: Flow<SoundEffect>
    abstract var gameIsInitialized: Boolean
    abstract fun resetGame(isGameRunning: Boolean = false, initialDirection: Int)
    abstract fun startGame(coroutineScope: CoroutineScope)
    abstract fun stopGame(coroutineScope: CoroutineScope)
    abstract suspend fun initializeGame()
}