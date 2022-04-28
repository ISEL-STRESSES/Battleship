package battleship.model

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame

class PlayerTest {
    @Test
    fun `Stress Test Player other`() {
        val playerA = Player.A
        val playerB = Player.B

        assertSame(playerA.other(), playerB)
        assertSame(playerB.other(), playerA)
        assertSame(playerA.other().other(), playerA)
        assertSame(playerB.other().other(), playerB)
    }

    @Test
    fun `Test get Player by id`() {

        val playerA = Player.A
        val playerB = Player.B

        assertEquals(playerA.id, 'A')
        assertEquals(playerB.id, 'B')
        assertSame(playerA.id + 1, playerB.id)
        assertSame(playerB.id - 1, playerA.id)
    }
}
