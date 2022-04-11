package model

import model.position.Position

const val BOARD_DIM = 10

data class Move(val player: Player, val shot: Shot, val status: ShotTarget)

class Board(val moves: List<Move> = emptyList(), val turn: Player = Player.A, val fleet: Fleet? = null, val winner: Player? = null)

/**
 * Makes a move by player [player] in position [pos].
 * @receiver The current board.
 * @return The board with the new move or null if this move is not possible.
 */
fun Board.play(pos: Position, player: Player): Board? =
    if (player != turn || winner != null || moves.any { it.shot.position === pos }) null
    else addMove(pos)

/**
 * Add a move by the current turn player in position [pos].
 * @receiver The current board.
 * @return The board with the new move.
 */
fun Board.addMove(pos: Position): Board =
    Board(moves + Move(turn, pos), turn.other(),null ,turn.takeIf { win(pos) })

/**
 * Returns the player who played in position [pos]
 * @return The player or null if position [pos] is free
 */
fun Board.get(pos: Position): Player? =
    moves.firstOrNull { it.shot.position === pos }?.player

/**
 * Check if the move in the position [pos] of the current player causes a win.
 * @return true if causes a win
 */
fun Board.win(pos: Position): Boolean {
    TODO("Not Yet Implemented")
}
