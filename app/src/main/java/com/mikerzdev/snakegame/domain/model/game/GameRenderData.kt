package com.mikerzdev.snakegame.domain.model.game

data class GameRenderData(
    val cellList: List<Cell>,
    val gameState: GameState,
) {
    companion object {
        fun empty() = GameRenderData(
            cellList = emptyList(),
            gameState = GameState.NotStarted
        )
    }
}