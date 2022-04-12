package battleship.model.board

import battleship.model.Position.Position
import battleship.model.Position.indexToColumn
import battleship.model.Position.indexToRow
import battleship.model.Ship.ShipType
import battleship.ui.BOARD_CHAR_DIM

open class Cell private constructor(val content: CellContent = CellContent.WATER){
    open val pos: Position = Position((-1).indexToColumn(), (-1).indexToRow()) // ?start at -1 to throw Illegal POS

    companion object {
        val values: List <Position> = listOf(BOARD_CHAR_DIM){ Position(/*?? é privado*/) }
        open fun toChar(): Char = content.toChar()
    }

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
