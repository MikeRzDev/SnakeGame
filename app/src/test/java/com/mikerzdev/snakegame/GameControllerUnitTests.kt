package com.mikerzdev.snakegame

import com.mikerzdev.snakegame.data.game.GameControllerImpl
import com.mikerzdev.snakegame.domain.consts.BOARD_X_CELLS
import com.mikerzdev.snakegame.domain.consts.BOARD_Y_CELLS
import com.mikerzdev.snakegame.domain.consts.HORIZONTAL_INITIAL_DIRECTION
import com.mikerzdev.snakegame.domain.consts.VERTICAL_INITIAL_DIRECTION
import com.mikerzdev.snakegame.domain.model.game.Coordinate
import com.mikerzdev.snakegame.domain.model.game.GameState
import com.mikerzdev.snakegame.domain.model.game.Snake
import com.mikerzdev.snakegame.domain.model.game.SnakeMetaData
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GameControllerUnitTests {

    @MockK(relaxed = true) private lateinit var mockLocalRepository: LocalRepository
    @MockK(relaxed = true) private lateinit var mockSnake1: Snake
    @MockK(relaxed = true) private lateinit var mockSnake2: Snake
    @MockK(relaxed = true) private lateinit var mockFood: Coordinate

    private lateinit var gameController: GameControllerImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        gameController = GameControllerImpl(mockLocalRepository, mockSnake1, mockSnake2, mockFood)
    }

    private fun prepareMockSnackes() {
        every { mockSnake1.body } returns arrayListOf()
        every { mockSnake1.metaData } returns SnakeMetaData.empty()
        every { mockSnake2.body } returns arrayListOf()
        every { mockSnake2.metaData } returns SnakeMetaData.empty()
    }

    @Test
    fun `Given a running game with horizontal direction, when reset game, then game is in PROGRESS state`() = runTest {
        // GIVEN
        val gameIsRunning = true
        val horizontalDirection = HORIZONTAL_INITIAL_DIRECTION
        prepareMockSnackes()

        // WHEN
        gameController.resetGame(gameIsRunning, horizontalDirection)

        // THEN
        verify {
            mockSnake1.resetSnake()
            mockSnake2.resetSnake()
        }
        assertEquals(0, mockSnake1.body[0].x)
        assertEquals(BOARD_X_CELLS - 1, mockSnake2.body[0].x)
        assertTrue(gameController.graphicsState.first().gameState is GameState.InProgress)
    }

    @Test
    fun `Given a NOT running game with vertical direction, when reset game, then game is in START state`() = runTest {
        // GIVEN
        val gameIsNotRunning = false
        val verticalDirection = VERTICAL_INITIAL_DIRECTION
        prepareMockSnackes()

        // WHEN
        gameController.resetGame(gameIsNotRunning, verticalDirection)

        // THEN
        verify {
            mockSnake1.resetSnake()
            mockSnake2.resetSnake()
        }
        assertEquals(0, mockSnake1.body[0].y)
        assertEquals(BOARD_Y_CELLS - 1, mockSnake2.body[0].y)
        assertTrue(gameController.graphicsState.first().gameState is GameState.StartGame)
    }

}