package com.mikerzdev.snakegame.domain.model.game

import com.mikerzdev.snakegame.domain.consts.BOARD_X_CELLS
import com.mikerzdev.snakegame.domain.consts.BOARD_Y_CELLS
import com.mikerzdev.snakegame.domain.consts.WINNER_COLOR
import com.mikerzdev.snakegame.presentation.utils.dimColor
import kotlin.math.abs

// this class was separated from Snake to ease sending it as a parameter to the GameBoard composable
data class SnakeMetaData(
    var color: ULong = 0UL,
    var name: String = "",
    var score: Int = 0,
    var isWinner: Boolean = false,
) {
    companion object {
        fun empty() = SnakeMetaData(
            color = 0UL,
            name = "",
        )
    }
}

data class Snake(
    var body: ArrayList<Coordinate> = arrayListOf(),
    var metaData: SnakeMetaData
) {
    val head get() = body.last()

    fun isHead(coordinate: Coordinate) = head == coordinate

    fun move(direction: Direction) {
        val newHead = when (direction) {
            Direction.STOP -> Coordinate(0, 0)
            Direction.UP -> Coordinate(head.x, head.y - 1)
            Direction.DOWN -> Coordinate(head.x, head.y + 1)
            Direction.LEFT -> Coordinate(head.x - 1, head.y)
            Direction.RIGHT -> Coordinate(head.x + 1, head.y)
        }
        if (!newHead.isZero()) {
            body.add(newHead)
        }
    }

    /*
    this algorithm checks the available directions relative to the snake head; checking possible
    collisions with the snake body, board and the other snake.
     */
    fun searchAvailableDirections(
        otherSnakeBody: List<Coordinate>,
        food: Coordinate
    ): ArrayList<AvailableDirection> {
        val availableDirections = arrayListOf<AvailableDirection>()

        val leftCoordinate = Coordinate(head.x - 1, head.y)
        if (leftCoordinate.x >= 0 && !body.contains(leftCoordinate) && !otherSnakeBody.contains(
                leftCoordinate
            )
        ) availableDirections.add(
            AvailableDirection(
                direction = Direction.LEFT,
                getDistanceFromFood(leftCoordinate, food)
            )
        )
        val rightCoordinate = Coordinate(head.x + 1, head.y)
        if (rightCoordinate.x < BOARD_X_CELLS && !body.contains(rightCoordinate) && !otherSnakeBody.contains(
                rightCoordinate
            )
        ) availableDirections.add(
            AvailableDirection(
                direction = Direction.RIGHT,
                getDistanceFromFood(rightCoordinate, food)
            )
        )
        val upCoordinate = Coordinate(head.x, head.y - 1)
        if (upCoordinate.y >= 0 && !body.contains(upCoordinate) && !otherSnakeBody.contains(
                upCoordinate
            )
        ) availableDirections.add(
            AvailableDirection(
                direction = Direction.UP,
                getDistanceFromFood(upCoordinate, food)
            )
        )
        val downCoordinate = Coordinate(head.x, head.y + 1)
        if (downCoordinate.y < BOARD_Y_CELLS && !body.contains(downCoordinate) && !otherSnakeBody.contains(
                downCoordinate
            )
        ) availableDirections.add(
            AvailableDirection(
                direction = Direction.DOWN,
                getDistanceFromFood(downCoordinate, food)
            )
        )

        return if (availableDirections.isEmpty()) {
            availableDirections.add(AvailableDirection(Direction.STOP, 0))
            availableDirections
        } else {
            availableDirections
        }
    }

    //Manhattan Distance - the secret sauce
    private fun getDistanceFromFood(head: Coordinate, food: Coordinate): Int {
        val xDistance = abs(food.x - head.x)
        val yDistance = abs(food.y - head.y)
        return xDistance + yDistance
    }

    fun increaseScore() = metaData.score++

    fun resetSnake() {
        body.clear()
        metaData.isWinner = false
    }

    val cells get(): List<Cell> {
        return body.map {
            val snakeColor =
                if (metaData.isWinner) WINNER_COLOR else metaData.color
            val color = if (isHead(it)) snakeColor else dimColor(snakeColor)
            Cell(it, color)
        }
    }

    fun clearScore() {
        metaData.score = 0
    }

}
