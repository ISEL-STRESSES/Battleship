package battleship.model.Board

import battleship.model.Position.ROW_DIM
import battleship.model.Position.COLUMN_DIM

const val BOARD_DIM = COLUMN_DIM * ROW_DIM

data class Board(
    val moves: List<Move> = emptyList(),
    val turn: Player = Player.CROSS,
    val winner: Player? = null
)
