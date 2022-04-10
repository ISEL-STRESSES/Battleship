package model.position

/**
 * Class that represents a position on the battleship board.
 * @property column X position.
 * @property row Y position.
 */
class Position(val column: Column, val row: Row) {
    companion object {
        val values = List(COLUMN_DIM * ROW_DIM) {
            Position((it / COLUMN_DIM).indexToColumn(), (it % ROW_DIM).indexToRow())
        }

        /**
         * Getter function for getting a position.
         * @param indexColumn ordinal of the column.
         * @param indexRow ordinal of the row.
         * @return Position.
         */
        operator fun get(indexColumn: Int, indexRow: Int): Position = values[indexColumn * COLUMN_DIM + indexRow]

        /**
         * Getter function for getting a position.
         * @param column key for the column.
         * @param row key for the row.
         * @return Position.
         */
        operator fun get(column: Column, row: Row): Position = values[column.ordinal * COLUMN_DIM + row.ordinal]
    }

    /**
     * Override of toString function for correct format.
     * @return String representation of a Position.
     */
    override fun toString(): String = "${column.letter}${row.number}"
}

/**
 * Function that converts a String into a [Position] or null if not possible.
 * @receiver String to convert.
 * @return Position or null.
 */
fun String.toPositionOrNull(): Position? {
    val column = first().toColumnOrNull() ?: return null
    val row = drop(1).toInt().toRowOrNull() ?: return null
    return Position[column, row]
}

/**
 * Function that converts a String into a [Position].
 *
 * @receiver String to convert.
 * @return Position.
 */
fun String.toPosition(): Position = toPositionOrNull() ?: throw IllegalStateException()
