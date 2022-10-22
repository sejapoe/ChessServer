package ru.sejapoe.chess.core.turn

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sejapoe.chess.core.Game
import ru.sejapoe.chess.core.PieceColor
import ru.sejapoe.chess.core.PieceMovementData

@Serializable
@SerialName("move")
class Move(
    override val performer: PieceColor,
    val move: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(game: Game) {
        super.perform(game)
        game.performMove(this)
    }
}