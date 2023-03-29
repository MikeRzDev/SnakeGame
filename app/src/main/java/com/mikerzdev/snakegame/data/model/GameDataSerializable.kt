package com.mikerzdev.snakegame.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GameDataSerializable(
    val snake1MetaData: SnakeMetadataSerializable,
    val snake2MetaData: SnakeMetadataSerializable,
)