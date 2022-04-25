package battleship.model.board

import battleship.model.PlayError
import battleship.model.ship.Ship
import battleship.model.ship.ShipType
import kotlin.math.max
import kotlin.math.min

typealias Fleet = List<Ship>
typealias Grid = Map<Position, Cell>
typealias Coords = Pair<Int, Int> // Pair<X, Y>

data class Board(val fleet: Fleet = emptyList(), val grid: Grid = mapOf())

/**
 * @brief returns the number of cells that the board has as ships
 */
fun Board.cellsQuantity(): Int {
    return this.fleet.fold(0) { acc, ship -> acc + ship.type.squares }
}

/**
 *
 */
fun Board.win() = (cellsQuantity() - grid.entries.count { (_, it) -> it is ShipSunk }) == 0

/**
 *
 */
data class Bounds(val topLeft: Coords, val bottomRight: Coords)

/**
 *
 */
fun getBounds(pos: Position, dir: Direction, size: Int): Bounds {
    val extension = Pair(if (dir == Direction.HORIZONTAL) size else 0, if (dir == Direction.VERTICAL) size else 0)
    return Bounds(
        max(pos.column.ordinal - 1, 0) to max(pos.row.ordinal - 1, 0),
        min(pos.column.ordinal + extension.first + 1, COLUMN_DIM) to min(
            pos.row.ordinal + extension.second + 1,
            ROW_DIM
        )
    )
}

fun Board.putShip(type: ShipType, pos: Position, direction: Direction): Board {
    // TODO: change exception to put result
    if (fleet.count { type == it.type } >= type.fleetQuantity)
        error("No more ${type.name} to put")

    if (direction == Direction.HORIZONTAL) {
        if (pos.column.ordinal + type.squares > COLUMN_DIM) error("Can't put ${type.name} in that position")
    } else if (pos.row.ordinal + type.squares > ROW_DIM) error("Can't put ${type.name} in that position")

    // get bounds of ship
    val bounds = getBounds(pos, direction, type.squares)

    // iterate through cells to see if all these cells are empty otherwise throw error
    for (y in bounds.topLeft.second until bounds.bottomRight.second) {
        for (x in bounds.topLeft.first until bounds.bottomRight.first) {
            if (this.grid[Position[x, y]] != null) error("Can't put ${type.name} in that position")
        }
    }

    val newShip = Ship(type, pos, direction, List(type.squares) { pos.movePosition(direction, it) })
    val cells = newShip.positions.map { currPos -> ShipCell(currPos, newShip) }

    val newFleet = fleet + newShip
    val newGrid = grid + cells.associateBy { it.pos }
    return Board(newFleet, newGrid)
}

fun Board.removeShip(pos: Position): Board {
    // get cell at position
    val cell = this.grid[pos]
    return if (cell is ShipCell) {
        val ship = cell.ship
        val newFleet = fleet - ship
        val newGrid = grid - ship.positions

        Board(newFleet, newGrid)
    } else {
        this
    }
}

enum class ShotConsequence {
    MISS, HIT, SUNK, INVALID
}

data class ShotResult(val board: Board, val consequence: ShotConsequence, val error: PlayError = PlayError.NONE)

fun Board.makeShot(pos: Position): ShotResult {
    when (val cell = grid[pos]) { // THE FUCK IS THIS
        is MissCell, is ShipHit -> {
            // Ship sunk is infered as an invalid shot because of its inherited from ShipHit
            return ShotResult(this, ShotConsequence.INVALID, PlayError.INVALID_SHOT)
        }
        is ShipCell -> {
            val ship = cell.ship
            val gridAfterShot = grid + (pos to ShipHit(pos, ship))
            // check if ship is sunk
            val hasSunk = ship.positions.all { grid[it] is ShipHit }
            return if (hasSunk) {
                val gridAfterSunk = grid + ship.positions.map { it to ShipSunk(it, ship) }
                ShotResult(copy(grid = gridAfterSunk), ShotConsequence.SUNK)
            } else {
                ShotResult(copy(grid = gridAfterShot), ShotConsequence.HIT)
            }
        }
        null -> {
            // Add cell to the grid with MissCell
            return ShotResult(copy(grid = grid + (pos to MissCell(pos))), ShotConsequence.MISS)
        }
    }
    return ShotResult(this, ShotConsequence.INVALID, PlayError.INVALID_SHOT)
}
