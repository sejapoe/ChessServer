package ru.sejapoe.chess.core

import ru.sejapoe.chess.core.PieceColor.BLACK
import ru.sejapoe.chess.core.PieceColor.WHITE
import ru.sejapoe.chess.core.PieceType.*
import ru.sejapoe.chess.core.turn.*
import kotlin.random.Random

class Game(val id: Long, val playerWhite: User, val playerBlack: User) {
    var history: MutableList<Turn> = mutableListOf()
    private var turn: PieceColor = WHITE
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
        get() = BoardData(board, history.lastOrNull(), turn, history.size)

    fun performMove(move: Move) {
        move(move.move)
    }

    fun performAttack(attack: Attack) {
        move(attack.move)
    }

    fun performCast(cast: Cast) {
        move(cast.rookMove)
        move(cast.kingMove)
    }

    fun performEnPassant(enPassant: EnPassant) {
        val (_, rowSource, columnDest, _) = enPassant.move
        move(enPassant.move)
        board[rowSource][columnDest] = null
    }

    private fun move(move: PieceMovementData) {
        val (columnSource, rowSource, columnDest, rowDest) = move
        board[rowDest][columnDest] = board[rowSource][columnSource]
        board[rowSource][columnSource] = null
    }

    fun perform(turn: Turn) {
        turn.perform(this)
        history.add(turn)
    }

    fun performPromotion(promotion: Promotion) {
        board[promotion.row][promotion.column] = promotion.pieceData
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