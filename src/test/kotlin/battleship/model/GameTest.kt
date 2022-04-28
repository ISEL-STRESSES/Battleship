package battleship.model

import battleship.model.board.Board
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals


class GameTest {
    @Test
    fun `check create game and checkPutState`() {
        val sut = createGame()
        assertDoesNotThrow { sut.state.checkPutState() }
        assertEquals(sut.name, "")
        assertEquals(sut.boardA, Board())
        assertEquals(sut.boardB, Board())
        val state = GameState.FIGHT
        assertThrows<IllegalStateException> { state.checkPutState() }
    }

}

