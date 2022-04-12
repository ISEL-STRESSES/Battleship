package battleship.model

import battleship.model.Game.Player
import battleship.model.Position.Position
import battleship.model.Position.indexToColumn
import battleship.model.Position.indexToRow
import model.*
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertSame

class BoardTest {
    @Test
    fun `Player normal usage`() {
        assertEquals('X', Player.A)
        assertEquals('O', Player.B.symbol)
        assertSame(Player.B, Player.A.other())
    }

    @Test
    fun `Position normal usage`() {
        val sut = Position(1.indexToColumn(), 2.indexToRow())
        assertEquals(BOARD_DIM + 2, sut.index)
        assertEquals(1, sut.column)
        assertEquals(2, sut.row)
    }

    @Test
    fun `Board normal usage`() {
        val board = Board().play(Position(1, 1), Player.A)
        assertNotNull(board)
        assertEquals(Player.A, board.get(Position(1, 1)))
        assertEquals(null, board.get(Position(0, 0)))
    }

    @Test
    fun `Winner diagonal 1`() {
        assertEquals(Player.A, plays(0, 1, 4, 3, 8).winner)
    }

    @Test
    fun `Winner diagonal 2`() {
        assertEquals(Player.A, plays(2, 0, 4, 1, 6).winner)
    }

    @Test
    fun `Winner line`() {
        assertEquals(Player.A, plays(3, 0, 4, 1, 5).winner)
    }

    @Test
    fun `Winner column`() {
        assertEquals(Player.A, plays(0, 1, 3, 2, 6).winner)
    }

    private fun plays(vararg indexes: Int) =
        indexes.fold(Board()) { board, idx -> board.addMove(Position.values[idx]) }
}
