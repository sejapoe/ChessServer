package ru.sejapoe.chess.core

import ru.sejapoe.chess.core.PieceColor.BLACK
import ru.sejapoe.chess.core.PieceColor.WHITE
import ru.sejapoe.chess.core.PieceType.*
import java.util.logging.Logger
import kotlin.random.Random

class Game(val id: Long, val playerWhite: User, val playerBlack: User) {
    private var turn: PieceColor = WHITE
    private var turnCount = 0
    private val board: MutableList<MutableList<PieceData?>> = MutableList(8) { r ->
        MutableList(8) { c ->
            when (r) {
                0 -> when (c) {
                    0, 7 -> ROOK(WHITE)
                    1, 6 -> KNIGHT(WHITE)
                    2, 5 -> BISHOP(WHITE)
                    3 -> QUEEN(WHITE)
                    4 -> KING(WHITE)
                    else -> null
                }

                1 -> PAWN(WHITE)
                6 -> PAWN(BLACK)
                7 -> when (c) {
                    0, 7 -> ROOK(BLACK)
                    1, 6 -> KNIGHT(BLACK)
                    2, 5 -> BISHOP(BLACK)
                    3 -> QUEEN(BLACK)
                    4 -> KING(BLACK)
                    else -> null
                }

                else -> null
            }
        }
    }
    val state: BoardData
        get() = BoardData(board, turn, turnCount)

    fun applyMove(data: PieceMovementData) {
        Logger.getGlobal().info(data.toString())
        val (columnSource, rowSource, columnDest, rowDest) = data
        if (columnDest != -1 && rowDest != -1) {
            board[rowDest][columnDest] = board[rowSource][columnSource]
            turn = !data.performer
        }
        board[rowSource][columnSource] = null
        turnCount++
    }

    companion object {
        private val coinFlipper = Random.Default
        val games: MutableMap<Long, Game> = mutableMapOf()
        val matchmaking: MutableSet<User> = mutableSetOf()

        var id = 0L

        fun addUserToQueue(user: User) = matchmaking.add(user)

        fun registerGame(player1: User, player2: User): Game = if (coinFlipper.nextBoolean()) {
            Game(++id, player1, player2).also { games[it.id] = it }
        } else {
            Game(++id, player2, player1).also { games[it.id] = it }
        }
    }
}