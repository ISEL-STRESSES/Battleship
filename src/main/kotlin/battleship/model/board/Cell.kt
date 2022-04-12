package battleship.model.board

import battleship.model.Position.Position
import battleship.model.Position.indexToRow
import battleship.model.Ship.ShipType
import indexToColumn

open class Cell {
    open val pos: Position = Position((-1).indexToColumn(), (-1).indexToRow()) // in this case class showed be open // throw Illegal POS
    open val content: CellContent = CellContent.WATER

    open fun toChar(): Char = content.toChar()
}

enum class CellContent(private val id: Char) {
    WATER(' '),
    SHIP('#'),
    HIT('*'),
    SUNK('X');

     fun toChar(): Char {
        return this.id
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
