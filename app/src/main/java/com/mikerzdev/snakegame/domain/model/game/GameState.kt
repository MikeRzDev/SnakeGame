package com.mikerzdev.snakegame.domain.model.game


sealed class GameState(
    val gameData: GameData = GameData.empty(),
) {
    object NotStarted : GameState()
    class StartGame(
        gameData: GameData
    ) : GameState(gameData)

    class InProgress(
        gameData: GameData
    ) : GameState(gameData)

    class TieGame(
        gameData: GameData
    ) : GameState(gameData)

    class GameOver(
        gameData: GameData,
        val winnerName: String
    ) : GameState(gameData)
}