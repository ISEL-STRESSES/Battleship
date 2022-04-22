package battleship.model.board

/**
 *
 */
enum class Direction {
    HORIZONTAL, VERTICAL
}

fun Position.movePosition(dir : Direction, length : Int) : Position
{
    val x = this.column.ordinal + if(dir === Direction.HORIZONTAL) length else 0
    val y = this.row.ordinal + if(dir === Direction.VERTICAL) length else 0
    return Position[x, y];
}

/**
 *
 */
fun String.toDirectionOrNull() : Direction?
{
    if(this.isBlank()) return null
    return Direction.values().firstOrNull { this[0].equals(it.name[0], true) }
}
