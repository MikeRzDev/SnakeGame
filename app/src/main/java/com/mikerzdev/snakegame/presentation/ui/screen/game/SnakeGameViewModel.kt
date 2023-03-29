package com.mikerzdev.snakegame.presentation.ui.screen.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mikerzdev.snakegame.domain.consts.HORIZONTAL_INITIAL_DIRECTION
import com.mikerzdev.snakegame.domain.consts.VERTICAL_INITIAL_DIRECTION
import com.mikerzdev.snakegame.domain.game.GameController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt


@HiltViewModel
class SnakeGameViewModel @Inject constructor(
    private val gameController: GameController
) : ViewModel() {

    val gameGraphicsLiveData = gameController.graphicsState.asLiveData()
    val gameSoundLiveData = gameController.soundState.asLiveData()

    fun startGame() {
        gameController.startGame(viewModelScope)
    }

    fun stopGame() {
        gameController.stopGame(viewModelScope)
    }

    fun resetGame() {
        if (!gameController.gameIsInitialized) {
            viewModelScope.launch {
                gameController.initializeGame()
            }.invokeOnCompletion {
                gameController.resetGame(initialDirection = generateInitialDirection())
            }
        } else {
            gameController.resetGame(initialDirection = generateInitialDirection())
        }
    }

    private fun generateInitialDirection() =
        Random.nextInt(HORIZONTAL_INITIAL_DIRECTION..VERTICAL_INITIAL_DIRECTION)

}
