package battleship.ui

import battleship.model.Board
import battleship.model.addMove
import battleship.model.position.Position
import battleship.model.position.indexToColumn
import battleship.model.position.indexToRow
import org.junit.jupiter.api.Test
import battleship.ui.printBoard

class OutputTest {
    @Test
    fun printBoardTest() {
        printBoard(Board().addMove(Position(1.indexToColumn(), 1.indexToRow())))
    }
}
