package ru.sejapoe.chess.core

import kotlinx.serialization.Serializable

@Serializable
data class PieceMovementData(
    val columnSource: Int,
    val rowSource: Int,
    val columnDest: Int,
    val rowDest: Int,
    val performer: PieceColor,
)