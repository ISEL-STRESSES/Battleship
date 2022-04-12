package battleship.model.position

// Max number of columns.
const val COLUMN_DIM = 10

/**
 * Class that represent a column in the BattleShip game.
 * @param letter identifier of the column.
 * @param ordinal position of a [letter] in all available columns.
 */
class Column(val letter: Char, val ordinal: Int) {
    companion object {
        val values = List(COLUMN_DIM) { Column('A' + it, it) }
    }
}

/**
 * Returns the first element matching the given Char with [Column.letter], or null if element was not found.
 * @receiver Char to convert to column.
 * @return Column or null.
 */
fun Char.toColumnOrNull(): Column? = Column.values.firstOrNull { it.letter == this.uppercaseChar() }

/**
 * Function that converts an ordinal to Column or null if not possible.
 * @receiver Integer.
 * @return Column or null.
 */
fun Int.indexToColumnOrNull(): Column? = Column.values.firstOrNull { it.ordinal == this }

/**
 * Function that converts an ordinal to Column.
 * Throws IndexOutOfBoundsException if not possible.
 * @receiver Integer.
 * @return Column or throws an exception.
 */
fun Int.indexToColumn(): Column = indexToColumnOrNull() ?: throw IndexOutOfBoundsException()
