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

open class ShipCell(pos: Position, val ship : Ship) : Cell(pos)

open class ShipHit(pos : Position, ship : Ship): ShipCell(pos, ship);

class ShipSunk(pos : Position, ship : Ship): ShipHit(pos, ship);

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
