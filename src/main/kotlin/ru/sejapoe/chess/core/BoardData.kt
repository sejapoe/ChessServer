package ru.sejapoe.chess.core

import kotlinx.serialization.Serializable

@Serializable
data class BoardData(
    val cells: MutableList<MutableList<PieceData?>>,
    val turn: PieceColor,
    val turnCount: Int
)