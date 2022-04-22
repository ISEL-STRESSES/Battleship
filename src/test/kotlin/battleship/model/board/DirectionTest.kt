
import battleship.model.board.Direction
import battleship.model.board.toDirectionOrNull
import kotlin.test.assertSame
import kotlin.test.Test

class DirectionTest
{
    @Test
    fun `String to Direction`()
    {
        assertSame(Direction.HORIZONTAL, "h".toDirectionOrNull());
        assertSame(Direction.VERTICAL, "v".toDirectionOrNull());
        assertSame(null, "k".toDirectionOrNull());
    }
}

