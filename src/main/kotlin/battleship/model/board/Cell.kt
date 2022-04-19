package battleship.model.board

import battleship.model.board.position.Position

enum class HitStatus {
    LIGMA, HIT, SUNK
}

/**
 * Represents an empty square
 * @property pos cell's position in the board
 */
abstract class Cell(val pos: Position)

class MissCell(pos: Position) : Cell(pos)

/*
class ShipCell(pos: Position, hitStatus: HitStatus) : Cell(pos)

enum class HitStatus{meninas}

 */

/*
enum class Direction(val dir: Char) {
    HORIZONTAL('H'), VERTICAL('V');

    companion object {
        fun toDirection(dir: Char) = valueOf(dir.toString().toUpperCase())
    }

    override fun toString(): String {
        return this.dir.toString()
    }
}
 */



