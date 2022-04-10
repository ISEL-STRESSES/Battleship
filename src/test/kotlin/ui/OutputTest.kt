package ui

import model.Board
import model.addMove
import model.position.Position
import model.position.indexToColumn
import model.position.indexToRow
import org.junit.jupiter.api.Test

class OutputTest {
    @Test
    fun printBoardTest() {
        printBoard(Board().addMove(Position(1.indexToColumn(), 1.indexToRow())))
    }
}
