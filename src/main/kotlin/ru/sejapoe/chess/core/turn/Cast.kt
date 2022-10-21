package ru.sejapoe.chess.core.turn

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sejapoe.chess.core.Game
import ru.sejapoe.chess.core.PieceColor
import ru.sejapoe.chess.core.PieceMovementData

@Serializable
@SerialName("cast")
class Cast(
    override val performer: PieceColor,
    val kingMove: PieceMovementData,
    val rookMove: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(game: Game) = game.performCast(this)
}