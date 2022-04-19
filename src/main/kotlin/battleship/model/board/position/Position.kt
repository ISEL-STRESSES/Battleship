package battleship.model.board.position

/**
 * All positions that make up the game
 * @property column describes position's column
 * @property row describes position's row
 */
class Position private constructor(val column: Column, val row: Row) {
    override fun toString() = "${column.letter}${row.number}"

    companion object {
        // private as to only get Positions from the .get() methods
        val values = List(COLUMN_DIM * ROW_DIM) {
            Position((it / COLUMN_DIM).indexToColumn(), (it % ROW_DIM).indexToRow())
        }

        // get all possible positions
        /**
         * Getter function for getting a position.
         * @param indexColumn ordinal of the column.
         * @param indexRow ordinal of the row.
         * @return Position.
         */
        operator fun get(indexColumn: Int, indexRow: Int) = values[indexColumn * COLUMN_DIM + indexRow]

        /**
         * Getter function for getting a position.
         * @param column key for the column.
         * @param row key for the row.
         * @return Position.
         */
        operator fun get(column: Column, row: Row) = get(column.ordinal, row.ordinal)
    }
}

/**
 * @brief Converts String into a Position. null if such position does not exist
 */
fun String.toPositionOrNull(): Position? {
    if (this.isBlank()) return null
    val left = this[0]
    if (!left.isLetter()) return null
    val right = this.drop(1).toIntOrNull() ?: return null
    val column = left.toColumnOrNull()
    val row = right.toRowOrNull()
    if (column == null || row == null) return null
    return Position[column, row]
}

/**
 * @brief Converts string to a Position
 */
fun String.toPosition(): Position {
    val result = this.toPositionOrNull()
    checkNotNull(result) { "Invalid String to convert into position!" }
    return result
}
