package battleship.model

import battleship.model.board.Position
import battleship.model.ship.Ship
import battleship.model.ship.ShipType

enum class HitStatus {
    LIGMA, HIT, SUNK
}

/**
 * Represents an empty square
 * @property pos cell's position in the board
 */
abstract class Cell(val pos: Position)

class MissCell(pos: Position) : Cell(pos)

class ShipCell(pos: Position) : Cell(pos)

/*
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
