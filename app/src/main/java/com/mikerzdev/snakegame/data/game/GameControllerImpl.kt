package com.mikerzdev.snakegame.data.game

import androidx.compose.ui.graphics.Color
import com.mikerzdev.snakegame.domain.consts.*
import com.mikerzdev.snakegame.domain.game.GameController
import com.mikerzdev.snakegame.domain.model.game.*
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random
import kotlin.random.nextInt


class GameControllerImpl(
    private val localRepository: LocalRepository,
    private var snake1: Snake = Snake(metaData = SnakeMetaData(Color.Green.value, "Player 1")),
    private var snake2: Snake = Snake(metaData = SnakeMetaData(Color.Red.value, "Player 2")),
    private var food: Coordinate = Coordinate.zero()
) : GameController() {

    private lateinit var gameJob: Job
    private val graphicsMutableState =
        MutableStateFlow(
            GameRenderData.empty()
        )
    private val soundMutableState =
        MutableSharedFlow<SoundEffect>(
            onBufferOverflow = BufferOverflow.DROP_OLDEST,
            replay = 1,
        )
    override val graphicsState: Flow<GameRenderData> = graphicsMutableState
    override val soundState: Flow<SoundEffect> = soundMutableState
    override var gameIsInitialized = false

    override fun resetGame(isGameRunning: Boolean, initialDirection: Int) {
        snake1.resetSnake()
        snake2.resetSnake()
        val xCells = BOARD_X_CELLS - 1
        val yCells = BOARD_Y_CELLS - 1

        // initialize snakes in opposite directions horizontally or vertically
        when (initialDirection) {
            HORIZONTAL_INITIAL_DIRECTION -> {
                val randomY = Random.nextInt(0..yCells)
                snake1.body.add(Coordinate(0, randomY))
                val randomY2 = Random.nextInt(0..yCells)
                snake2.body.add(Coordinate(xCells, randomY2))

            }
            VERTICAL_INITIAL_DIRECTION -> {
                val randomX = Random.nextInt(0..xCells)
                snake1.body.add(Coordinate(randomX, 0))
                val randomX2 = Random.nextInt(0..xCells)
                snake2.body.add(Coordinate(randomX2, yCells))
            }
        }

        // initialize food in a random position, preferably near the center of the board to make the game more interesting :)
        food = Coordinate(Random.nextInt(2..xCells - 2), Random.nextInt(2..yCells - 2))
        if (isGameRunning) {
            drawGameBoard(GameState.InProgress(GameData(snake1.metaData, snake2.metaData)))
        } else {
            drawGameBoard(GameState.StartGame(GameData(snake1.metaData, snake2.metaData)))
        }
    }

    override fun startGame(coroutineScope: CoroutineScope) {
        gameJob = coroutineScope.launch {
            while (true) {
                delay(GAME_SPEED)
                val gameState = playGame()
                soundMutableState.emit(SoundEffect.SNAKE_1_MOVE)
                drawGameBoard(gameState)
                if (gameState is GameState.GameOver || gameState is GameState.TieGame) {
                    soundMutableState.emit(SoundEffect.WINNER)
                    delay(GAME_OVER_DELAY)
                    resetGame(isGameRunning = true, initialDirection = generateInitialDirection())
                }
            }
        }
    }

    override fun stopGame(coroutineScope: CoroutineScope) {
        gameJob.cancel()
        // only save non trivial scores
        if (snake1.metaData.score > 0 || snake2.metaData.score > 0) {
            coroutineScope.launch {
                localRepository.saveScore(
                    GameData(snake1.metaData, snake2.metaData)
                )
            }
        }
        snake1.clearScore()
        snake2.clearScore()
        drawGameBoard(GameState.NotStarted)
    }


    override suspend fun initializeGame() {
        coroutineScope {
            launch {
                val gameSettings = localRepository.getGameSettings()
                gameSettings?.snake1Data?.let { storedMetadata ->
                    if (storedMetadata.color != 0UL) {
                        snake1.metaData.color = storedMetadata.color
                    }
                    if (storedMetadata.name.isNotEmpty()) {
                        snake1.metaData.name = storedMetadata.name
                    }
                }

                gameSettings?.snake2Data?.let { storedMetadata ->
                    if (storedMetadata.color != 0UL) {
                        snake2.metaData.color = storedMetadata.color
                    }
                    if (storedMetadata.name.isNotEmpty()) {
                        snake2.metaData.name = storedMetadata.name
                    }
                }

            }
        }.invokeOnCompletion {
            gameIsInitialized = true
        }
    }


    private fun playGame(): GameState {
        decideSnakeMove(snake1, otherSnake = snake2, food)
        decideSnakeMove(snake2, otherSnake = snake1, food)

        val snake1Win = snakeAteFood(snake1)
        val snake2Win = snakeAteFood(snake2)

        return if (snake1Win && snake2Win) {
            GameState.TieGame(GameData(snake1.metaData, snake2.metaData))
        } else if (snake1Win) {
            isWinner(snake1)
        } else if (snake2Win) {
            isWinner(snake2)
        } else {
            GameState.InProgress(GameData(snake1.metaData, snake2.metaData))
        }
    }

    private fun isWinner(snake: Snake): GameState {
        snake.metaData.isWinner = true
        snake.increaseScore()
        return GameState.GameOver(
            GameData(snake1.metaData, snake2.metaData),
            winnerName = snake.metaData.name
        )
    }

    private fun decideSnakeMove(snake: Snake, otherSnake: Snake, food: Coordinate) {
        val snakeDirections =
            snake.searchAvailableDirections(otherSnakeBody = otherSnake.body, food)
        // sort the possible directions by the distance to the food; the best direction has the shortest distance
        val snakeSelectedDirection = snakeDirections.minByOrNull { it.distanceToFood }

        snakeSelectedDirection?.let { direction ->
            snake.move(direction.direction)
        }
    }

    private fun getGameCells(): List<Cell> {
        val snake1Cells = snake1.cells
        val snake2Cells = snake2.cells
        val foodCell = Cell(food, FOOD_COLOR)
        // combine all cells into one list
        return snake1Cells + snake2Cells + foodCell
    }

    private fun drawGameBoard(gameState: GameState) {
        val gameCells = if (gameState is GameState.NotStarted) {
            emptyList()
        } else {
            getGameCells()
        }
        graphicsMutableState.update {
            it.copy(
                gameState = gameState,
                cellList = gameCells,
            )
        }
    }

    private fun snakeAteFood(snake: Snake) = snake.head == food

    private fun generateInitialDirection() =
        Random.nextInt(HORIZONTAL_INITIAL_DIRECTION..VERTICAL_INITIAL_DIRECTION)

}