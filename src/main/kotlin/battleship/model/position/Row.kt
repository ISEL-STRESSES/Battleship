package battleship.model.position

const val ROW_DIM = 10
const val ROW_FIRST_NUMBER = 1

/**
 * All Rows that make up the game.
 * @property number Digit associated with the position, starting from ROW_FIRST_NUMBER
 * @property ordinal Row's index in the board
 */
class Row private constructor(idx: Int) {
    val number = ROW_FIRST_NUMBER + idx
    val ordinal = idx

    companion object {
        val values = List(ROW_DIM) { Row(it) }
    }
}

/**
 * @brief return a object according to its digit position, null if it does not exist
 */
fun Int.toRowOrNull() = Row.values.elementAtOrNull(this - ROW_FIRST_NUMBER)

/**
 * @brief return a object according to its index, null if it does not exit
 */
fun Int.indexToRowOrNull() = Row.values.elementAtOrNull(this)

/**
 * @brief return a row object according to its index
 * @throws IndexOutOfBoundsException
 */
fun Int.indexToRow() = Row.values[this]
