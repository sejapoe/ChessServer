package ru.sejapoe.chess.core.turn

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.sejapoe.chess.core.Game
import ru.sejapoe.chess.core.PieceColor
import ru.sejapoe.chess.core.PieceMovementData

@Serializable
@SerialName("attack")
class Attack(
    override val performer: PieceColor,
    val move: PieceMovementData,
    override val number: Int,
) : Turn() {
    override fun perform(game: Game) {
        super.perform(game)
        game.performAttack(this)
    }
}