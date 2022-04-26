package battleship.model.board

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
            Position((it % COLUMN_DIM).indexToColumn(), (it / COLUMN_DIM).indexToRow())
        }

        // get all possible positions
        /**
         * Getter function for getting a position.
         * @param indexColumn ordinal of the column.
         * @param indexRow ordinal of the row.
         * @return Position.
         * @throws IndexOutOfBoundsException
         */
        operator fun get(indexColumn: Int, indexRow: Int) : Position
        {
            if(indexColumn >= COLUMN_DIM || indexRow >= ROW_DIM)
                throw IndexOutOfBoundsException("Exceeded position bounds")
            return values[indexRow * COLUMN_DIM + indexColumn]
        }

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
 * Converts String into a Position, null if such position does not exist
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
 * Converts string to a Position
 */
fun String.toPosition(): Position {
    return toPositionOrNull() ?: error("Invalid Position $this")
}

/**
 * @throws IndexOutOfBoundsException if resulting position is invalid
 */
fun Position.movePosition(dir: Direction, length: Int): Position {
    val x = this.column.ordinal + if (dir === Direction.HORIZONTAL) length else 0
    val y = this.row.ordinal + if (dir === Direction.VERTICAL) length else 0
    return Position[x, y]
}
