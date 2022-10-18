package ru.sejapoe.chess.core

import kotlinx.serialization.Serializable

@Serializable
data class PieceData(
    val type: PieceType,
    val color: PieceColor
)

enum class PieceColor {
    WHITE, BLACK;

    operator fun not(): PieceColor =
        if (this == BLACK) WHITE else BLACK
}

enum class PieceType {
    KING, QUEEN, BISHOP, KNIGHT, ROOK, PAWN;

    operator fun invoke(pieceColor: PieceColor) = PieceData(this, pieceColor)
}
