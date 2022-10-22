package ru.sejapoe.chess.core.turn

import kotlinx.serialization.Serializable
import ru.sejapoe.chess.core.Game
import ru.sejapoe.chess.core.PieceData

@Serializable
class Promotion(
    val row: Int,
    val column: Int,
    val pieceData: PieceData,
) {
    fun perform(game: Game) = game.performPromotion(this)
}