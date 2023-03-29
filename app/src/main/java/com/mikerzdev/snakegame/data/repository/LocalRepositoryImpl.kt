package com.mikerzdev.snakegame.data.repository

import com.mikerzdev.snakegame.data.mapper.toData
import com.mikerzdev.snakegame.data.mapper.toDomain
import com.mikerzdev.snakegame.data.model.GameDataSerializable
import com.mikerzdev.snakegame.data.source.local.DataStoreManager
import com.mikerzdev.snakegame.data.source.local.PreferenceKeys
import com.mikerzdev.snakegame.domain.model.game.GameData
import com.mikerzdev.snakegame.domain.repository.LocalRepository
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class LocalRepositoryImpl(private val dataStore: DataStoreManager) : LocalRepository {

    override suspend fun saveScore(score: GameData) {
        val result = dataStore.getData(PreferenceKeys.SCORE_KEY).first()
        val scoreList = if (result.isNullOrEmpty()) {
            listOf()
        } else {
            Json.decodeFromString<List<GameDataSerializable>>(result)
        }
        val newScoreList = scoreList + score.toData()
        dataStore.setData(PreferenceKeys.SCORE_KEY, Json.encodeToString(newScoreList))
    }


    override suspend fun getScoreList(): List<GameData> {
        val scoreList = dataStore.getData(PreferenceKeys.SCORE_KEY).first()
        return if (scoreList.isNullOrEmpty()) {
            listOf()
        } else {
            Json.decodeFromString<List<GameDataSerializable>>(scoreList).map { it.toDomain() }
        }
    }

    override suspend fun saveGameSettings(gameSettings: GameData) {
        dataStore.setData(
            PreferenceKeys.GAME_SETTINGS_KEY,
            Json.encodeToString(gameSettings.toData())
        )
    }

    override suspend fun getGameSettings(): GameData? {
        val gameSettings = dataStore.getData(PreferenceKeys.GAME_SETTINGS_KEY).first()
        return  if (gameSettings.isNullOrEmpty()) {
            null
        } else {
            val gameSettingsSerializable = Json.decodeFromString<GameDataSerializable>(gameSettings)
            gameSettingsSerializable.toDomain()
        }
    }

}