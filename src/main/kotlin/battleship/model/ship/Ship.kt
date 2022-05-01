package battleship.model.ship

import battleship.model.board.Direction
import battleship.model.board.Position

/**
 * Class to represent a rectangle
 * @property topLeft top left position
 * @property bottomRight bottom right position
 */
data class Bounds(val topLeft: Position, val bottomRight: Position)

fun Bounds.toList(): List<Position> {
    val list = mutableListOf<Position>(); //TODO remove this mutability
    for (x in topLeft.column.ordinal..bottomRight.column.ordinal) {
        for (y in topLeft.row.ordinal..bottomRight.row.ordinal) {
            list.add(Position[x, y])
        }
    }
    return list
}

fun Bounds.collidesWith(other: Bounds): Boolean {
    return topLeft.column.ordinal <= other.topLeft.column.ordinal &&
            bottomRight.column.ordinal <= other.bottomRight.column.ordinal &&
            topLeft.row.ordinal >= other.topLeft.row.ordinal &&
            bottomRight.row.ordinal <= other.bottomRight.row.ordinal
}

fun Position.isInside(bounds: Bounds): Boolean {
    return column.ordinal >= bounds.topLeft.column.ordinal &&
            row.ordinal >= bounds.topLeft.row.ordinal &&
            column.ordinal <= bounds.bottomRight.column.ordinal &&
            row.ordinal <= bounds.bottomRight.row.ordinal
}

/**
 * Ship class in the game.
 * @property type type of the [Ship]
 * @property head head position of the [Ship]
 * @property dir direction of the [Ship]
 * @property positions list of positions of the [Ship].
 *
 */
data class Ship(
    val type: ShipType,
    val head: Position,
    val dir: Direction,
    val positions: List<Position>,
    val bounds: Bounds
)
