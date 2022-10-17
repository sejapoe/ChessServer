package ru.sejapoe.chess.core

import ru.sejapoe.chess.core.Piece.*
import java.util.logging.Logger

class Game(val id: Long, val player1: User, val player2: User) {
    val board: MutableList<MutableList<Piece?>> = MutableList(8) { r ->
        MutableList(8) { c ->
            when (r) {
                0 -> when (c) {
                    0, 7 -> ROOK_WHITE
                    1, 6 -> KNIGHT_WHITE
                    2, 5 -> BISHOP_WHITE
                    3 -> QUEEN_WHITE
                    4 -> KING_WHITE
                    else -> null
                }

                1 -> PAWN_WHITE
                6 -> PAWN_BLACK
                7 -> when (c) {
                    0, 7 -> ROOK_BLACK
                    1, 6 -> KNIGHT_BLACK
                    2, 5 -> BISHOP_BLACK
                    3 -> QUEEN_BLACK
                    4 -> KING_BLACK
                    else -> null
                }

                else -> null
            }
        }
    }

    fun applyMove(data: PieceMovementData) {
        Logger.getGlobal().info(data.toString())
        val (columnSource, rowSource, columnDest, rowDest) = data
        if (columnDest != -1 && rowDest != -1) {
            board[rowDest][columnDest] = board[rowSource][columnSource]
        }
        board[rowSource][columnSource] = null
    }

    companion object {
        val games: MutableMap<Long, Game> = mutableMapOf()
        val matchmaking: MutableSet<User> = mutableSetOf()

        var id = 0L

        fun addUserToQueue(user: User) = matchmaking.add(user)

        fun registerGame(player1: User, player2: User): Game = Game(++id, player1, player2).also { games[it.id] = it }
    }
}