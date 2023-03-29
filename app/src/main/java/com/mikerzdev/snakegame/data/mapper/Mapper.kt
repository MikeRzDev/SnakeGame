package com.mikerzdev.snakegame.data.mapper

import com.mikerzdev.snakegame.data.model.GameDataSerializable
import com.mikerzdev.snakegame.data.model.SnakeMetadataSerializable
import com.mikerzdev.snakegame.domain.model.game.GameData
import com.mikerzdev.snakegame.domain.model.game.SnakeMetaData

fun SnakeMetaData.toData() = SnakeMetadataSerializable(color, name, score)
fun GameData.toData() = GameDataSerializable(snake1Data.toData(), snake2Data.toData())
fun SnakeMetadataSerializable.toDomain() = SnakeMetaData(color, name, score)
fun GameDataSerializable.toDomain() = GameData(snake1MetaData.toDomain(), snake2MetaData.toDomain())
