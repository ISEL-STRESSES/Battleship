package battleship.model.board

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertSame

class CellTest {

    @Test
    fun `emptyCell`() {
        val sut = Cell()
    }

    @Test
    fun `Stardard Cell`
