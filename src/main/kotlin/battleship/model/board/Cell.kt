package battleship.model.board

import battleship.model.Position.Position
import battleship.model.Position.indexToColumn
import battleship.model.Position.indexToRow
import battleship.model.Ship.ShipType

/**
 * Represents an empty square
 * @property pos cell's position in the board
 */
open class Cell(val pos : Position) {

}

class ShipCell(pos: Position, val ship : Ship) : Cell(pos)

enum class CellContent {
    WATER, SHIP, HIT, SUNK;

    fun toChar(): Char {
        return when(this) {
            WATER -> ' '
            SHIP -> '#'
            HIT -> '*'
            SUNK -> 'X'
        }
    }
}

class ShipCell(val pos: Position, val ship: ShipType) : Cell()

enum class Direction(val dir: Char) {
    HORIZONTAL('H'), VERTICAL('V');

    override fun toString(): String {
        return this.dir.toString()
    }
}

class Ship(val pos: Position, val type: ShipType, val dir: Direction) : ShipCell(pos, type)
