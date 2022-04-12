package battleship.ui

import battleship.model.Board
import battleship.model.Position.Position
import battleship.model.Position.indexToColumn
import battleship.model.Position.indexToRow
import battleship.model.addMove
import org.junit.jupiter.api.Test

class OutputTest {
    @Test
    fun printBoardTest() {
        printBoard(Board().addMove(Position(1.indexToColumn(), 1.indexToRow())))
    }
}
