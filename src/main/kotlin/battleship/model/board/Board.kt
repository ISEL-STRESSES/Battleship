package battleship.model.board

import battleship.model.Game.Player
import battleship.model.Position.COLUMN_DIM
import battleship.model.Position.Position
import battleship.model.Position.ROW_DIM
import org.graalvm.compiler.lir.aarch64.AArch64Move

const val BOARD_DIM = COLUMN_DIM * ROW_DIM

data class Board(
    val moves: List<Move> = emptyList(),
    val turn: Player = Player.CROSS,
    val winner: Player? = null

)
