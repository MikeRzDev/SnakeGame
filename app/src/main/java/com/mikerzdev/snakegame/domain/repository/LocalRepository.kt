package com.mikerzdev.snakegame.domain.repository

import com.mikerzdev.snakegame.domain.model.game.GameData

interface LocalRepository {
    suspend fun saveScore(score: GameData)
    suspend fun getScoreList() : List<GameData>
    suspend fun saveGameSettings(gameSettings: GameData)
    suspend fun getGameSettings() : GameData?
}