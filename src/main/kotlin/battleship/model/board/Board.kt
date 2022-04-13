package battleship.model.board

import battleship.model.Game.Player
import battleship.model.Position.COLUMN_DIM
import battleship.model.Position.Position
import battleship.model.Position.ROW_DIM
import org.graalvm.compiler.lir.aarch64.AArch64Move

const val BOARD_DIM = COLUMN_DIM * ROW_DIM

data class Board(
    val cells : List<Cell>, // = List(BOARD_DIM){Cell(Position(it))},
    val moves: List<AArch64Move.Move> = emptyList(),

)
