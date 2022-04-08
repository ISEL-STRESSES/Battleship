package ui

import model.Board
import model.addMove
import model.position.Position
import org.junit.jupiter.api.Test

class OutputTest {
    @Test
    fun printBoardTest() {
        printBoard(Board().addMove(Position(1, 1)))
    }
}
