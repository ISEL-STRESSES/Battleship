package battleship.model.board.position

const val COLUMN_DIM = 10
const val COLUMN_FIRST_LETTER = 'A'

/**
 * All Rows that make up the game.
 * @property letter Character associated with the position, starting from COLUMN_FIRST_LETTER
 * @property ordinal Columns's index in the board
 */
class Column private constructor(idx: Int) {
    val letter = COLUMN_FIRST_LETTER + idx
    val ordinal = idx

    companion object {
        val values = List(COLUMN_DIM) { Column(it) }
    }
}

/**
 * @brief return a column according to its letter position, null if it does not exist
 */
fun Char.toColumnOrNull() = Column.values.elementAtOrNull(this.uppercaseChar() - COLUMN_FIRST_LETTER)

/**
 * @brief return a column according to its index, null if it does not exit
 */
fun Int.indexToColumnOrNull() = Column.values.elementAtOrNull(this)

/**
 * @brief return a column object according to its index
 * @throws IndexOutOfBoundsException
 */
fun Int.indexToColumn() = Column.values[this]
