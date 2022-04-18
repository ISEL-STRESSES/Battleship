package battleship.model.board

import battleship.model.position.Position
import battleship.model.ship.ShipType

/**
 * Represents an empty square
 * @property pos cell's position in the board
 */
data class Cell(val pos: Position, val content: CellContent)

data class ShipCell(val cell: Cell, val shipType: ShipType)

enum class CellContent {
    WATER, SHIP, HIT, SUNK, MISS, BORDER;

    fun toChar(): Char {
        return when (this) {
            WATER -> ' '
            SHIP -> '#'
            HIT -> '*'
            SUNK -> 'X'
            MISS -> 'O'
            BORDER -> 'B' // hide this shit
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
