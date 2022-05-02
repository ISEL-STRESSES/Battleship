package battleship.model.board

import battleship.model.ship.toShipType
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

class BoardTest {

    @Test
    fun `test placing ship`() {
        val emptyBoard = Board()
        val targetPos = "a1".toPosition()
        val sut = emptyBoard.putShip("5".toShipType(), targetPos, "h".toDirection())

        //check put result
        assertSame(sut.second, PutConsequence.NONE)
        //check fleet size
        assertEquals(sut.first.fleet.size, 1)
        //check positions and ship
        val ship = sut.first.fleet.first()
        ship.positions.forEach {
            val cell = sut.first.grid[it]
            assertTrue {
                cell is ShipCell && cell.ship === ship
            }
        }
    }

    @Test
    fun `test placing ship wrong`() {
        val emptyBoard = Board()
        val targetPos = Position[COLUMN_DIM - 1, ROW_DIM - 1]
        val sut = emptyBoard.putShip("5".toShipType(), targetPos, "h".toDirection())

        //check put result
        assertSame(sut.second, PutConsequence.INVALID_POSITION)
        //check fleet size
        assertEquals(sut.first.fleet.size, 0)
        //check board
        assertEquals(sut.first.grid.size, 0)
    }

    @Test
    fun `remove a ship from board`() {
        val emptyBoard = Board()
        val targetPos = "a1".toPosition()
        val sut = emptyBoard.putShip("5".toShipType(), targetPos, "h".toDirection())
        assertEquals(sut.first.removeShip(targetPos), emptyBoard)
    }

}
