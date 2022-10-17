package ru.sejapoe.chess.core

data class PieceMovementData(
    val columnSource: Int,
    val rowSource: Int,
    val columnDest: Int,
    val rowDest: Int,
)