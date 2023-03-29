package com.mikerzdev.snakegame.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SnakeMetadataSerializable(
    @SerialName("color")
    val color: ULong,
    @SerialName("name")
    val name: String,
    @SerialName("score")
    val score: Int = 0
)