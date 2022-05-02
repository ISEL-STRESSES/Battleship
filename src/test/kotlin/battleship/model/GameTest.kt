package battleship.model

import battleship.model.board.*
import battleship.model.ship.toShipType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue


class GameTest {
    @Test
    fun `check create game and checkPutState`() {
        val sut = createGame()
        assertDoesNotThrow { check(sut.state === GameState.SETUP) }
        assertEquals(sut.name, "")
        assertEquals(sut.boardA, Board())
        assertEquals(sut.boardB, Board())
        val state = GameState.FIGHT
        assertThrows<IllegalStateException> { check(sut.state === GameState.FIGHT) }
    }

    @Test
    fun `Test put command`() {
        val initialGame = createGame()
        val targetPos = "a1".toPosition()
        val sut = initialGame.putShip("5".toShipType(), targetPos, "h".toDirection())

        //check put result
        assertSame(sut.second, PutConsequence.NONE)
        //check fleet size
        assertEquals(sut.first.boardA.fleet.size, 1)
        //check positions and ship
        val ship = sut.first.boardA.fleet.first()
        sut.first.boardA.fleet[0].positions.forEach {
            val cell = sut.first.boardA.grid[it]
            assertTrue {
                cell is ShipCell && cell.ship === ship
            }
        }
    }

    @Test
    fun `Test invalid put command`() {
        val initialGame = createGame()
        val targetPos = Position[COLUMN_DIM - 1, ROW_DIM - 1]
        val sut = initialGame.putShip("5".toShipType(), targetPos, "h".toDirection())

        //check put result
        assertSame(sut.second, PutConsequence.INVALID_POSITION)
        //check fleet size
        assertEquals(sut.first.boardA.fleet.size, 0)
        //check board
        assertEquals(sut.first.boardA.grid.size, 0)
    }

    @Test
    fun `Test remove one ship`() {
        val initialGame = createGame()
        val targetPos = "a1".toPosition()
        val sut = initialGame.putShip("5".toShipType(), targetPos, "h".toDirection())
        assertEquals(sut.first.removeShip(targetPos), initialGame)
    }

    @Test
    fun `Test Remove All`() {
        val initialGame = createGame()
        val sut = initialGame.putShip("4".toShipType(), "a1".toPosition(), "h".toDirection()).first
            .putShip("4".toShipType(), "a3".toPosition(), "h".toDirection())
        assertEquals(sut.first.removeAll(), initialGame)
    }

}

