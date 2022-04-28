package battleship.model

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

