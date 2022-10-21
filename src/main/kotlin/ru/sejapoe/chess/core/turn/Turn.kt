package ru.sejapoe.chess.core.turn

import kotlinx.serialization.Serializable
import ru.sejapoe.chess.core.Game
import ru.sejapoe.chess.core.PieceColor

@Serializable
sealed class Turn() {
    abstract val number: Int
    abstract val performer: PieceColor
    abstract fun perform(game: Game)
}
/*
Turns:

performer: PieceColor

Move:
x1, y1, x2, y2 (One PMD)
condition # target cell empty

Attack:
x1, y1, x2, y2 (One PMD)
condition # target cell not empty

En passant:
x1, y1, x2, y2 (One PMD)
condition # last turn == target Pawn moved 2 cells
          # performer piece == pawn

Cast:
King & Rook (Two PMD)
condition # both not moved. cells between not under attack

-------
performTo(board)


 */