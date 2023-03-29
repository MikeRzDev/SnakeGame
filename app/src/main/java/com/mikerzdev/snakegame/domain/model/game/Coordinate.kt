package com.mikerzdev.snakegame.domain.model.game

data class Coordinate(val x: Int, val y: Int) {
    companion object {
        fun zero() = Coordinate(0, 0)
    }

    fun isZero() = x == 0 && y == 0
}