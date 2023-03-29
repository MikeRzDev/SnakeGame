package com.mikerzdev.snakegame.domain.model.game

data class GameData(var snake1Data: SnakeMetaData, var snake2Data: SnakeMetaData) {
    companion object {
        fun empty() = GameData(SnakeMetaData.empty(), SnakeMetaData.empty())
    }
}
