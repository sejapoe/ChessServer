package ru.sejapoe.chess.core

import kotlinx.serialization.Serializable
import ru.sejapoe.chess.core.turn.Turn

@Serializable
data class BoardData(
    val cells: MutableList<MutableList<PieceData?>>,
    val lastMove: Turn?,
    val turn: PieceColor,
    val turnCount: Int,
)