package com.mikerzdev.snakegame.domain.model.game

data class AvailableDirection(
    val direction: Direction,
    val distanceToFood: Int
)