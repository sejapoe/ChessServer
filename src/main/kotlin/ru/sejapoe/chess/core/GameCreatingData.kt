package ru.sejapoe.chess.core

import kotlinx.serialization.Serializable

@Serializable
data class GameCreatingData(
    val id: Long,
    val yourColor: PieceColor
)