package model.position

// Max number of rows.
const val ROW_DIM = 10

/**
 * Class that represent a column in the BattleShip game.
 * @param number identifier of the row.
 * @param ordinal position of a [number] in all available columns.
 */
class Row(val number: Int, val ordinal: Int) {
    companion object {
        val values = List(ROW_DIM) { Row(it + 1, it) }
    }
}

/**
 * Returns the first element matching the given Integer with [Row.number], or null if element was not found.
 * @receiver Integer to Convert to Row.
 * @return Row or null
 */
fun Int.toRowOrNull(): Row? = Row.values.firstOrNull { it.number == this }

/**
 * Function that converts an ordinal to Row or null if not possible.
 * @receiver Integer.
 * @return Row or null.
 */
fun Int.indexToRowOrNull(): Row? = Row.values.firstOrNull { it.ordinal == this }

/**
 * Function that converts an ordinal to row.
 * Throws IndexOutOfBoundsException if not possible.
 * @receiver Integer.
 * @return row or throws an exception.
 */
fun Int.indexToRow() = indexToRowOrNull() ?: throw IndexOutOfBoundsException()
