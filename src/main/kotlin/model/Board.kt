package model

import model.position.Position

const val BOARD_DIM = 10


data class Move(val pos: Position, val player: Player)

class Board(val moves: List<Move> = emptyList(), val turn: Player = Player.A, val winner: Player? = null)

/**
 * Makes a move by player [player] in position [pos].
 * @receiver The current board.
 * @return The board with the new move or null if this move is not possible.
 */
fun Board.play(pos: Position, player: Player): Board? =
    if (player != turn || winner != null || moves.any { it.pos === pos }) null
    else addMove(pos)

/**
 * Add a move by the current turn player in position [pos].
 * @receiver The current board.
 * @return The board with the new move.
 */
fun Board.addMove(pos: Position): Board =
    Board(moves + Move(pos, turn), turn.other(), turn.takeIf { win(pos) })

/**
 * Returns the player who played in position [pos]
 * @return The player or null if position [pos] is free
 */
fun Board.get(pos: Position): Player? =
    moves.firstOrNull { it.pos === pos }?.player

/**
 * Check if the move in the position [pos] of the current player causes a win.
 * @return true if causes a win
 */
fun Board.win(pos: Position): Boolean {
    TODO("Not Yet Implemented")
}
