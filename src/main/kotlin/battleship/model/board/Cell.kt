package battleship.model.board

import battleship.model.Position.Position
import battleship.model.Ship.ShipType

/**
 * Represents an empty square
 * @property pos cell's position in the board
 */
data class Cell(val pos: Position)

data class ShipCell(val cell: Cell, val shipType: ShipType)

enum class CellContent {
    WATER, SHIP, HIT, SUNK, MISS;

    fun toChar(): Char {
        return when (this) {
            WATER -> ' '
            SHIP -> '#'
            HIT -> '*'
            SUNK -> 'X'
            MISS -> 'O'
        }
    }
}

enum class Direction(val dir: Char) {
    HORIZONTAL('H'), VERTICAL('V');

    companion object {
        fun toDirection(dir: Char) = valueOf(dir.toString().toUpperCase())
    }

    override fun toString(): String {
        return this.dir.toString()
    }
}
