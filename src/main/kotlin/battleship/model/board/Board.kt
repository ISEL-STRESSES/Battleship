package battleship.model.board

import battleship.model.PlayError
import battleship.model.ShotConsequence
import battleship.model.ship.Ship
import battleship.model.ship.ShipType
import battleship.model.ship.positions
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

    val newShip = Ship(type, pos, direction)
    val cells = newShip.positions().map { currPos -> ShipCell(currPos, newShip) }

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
        val newGrid = grid - ship.positions()

        Board(newFleet, newGrid)
    } else {
        this
    }
}

data class BoardResult(val board: Board, val consequence: ShotConsequence, val error: PlayError = PlayError.NONE)

fun Board.makeShot(pos: Position): BoardResult {
    val elemToFind = grid[pos]
    val newGrid: Grid
    if (elemToFind != null) {
        //Check if elem is ShipCell, if yes make ShipHit, else "Position Already Shot"
        val cell = grid.entries.first { it.key == pos } //it.positions().any{ shipCell -> shipCell == pos}}.positions()
        return if (cell.value is ShipCell) { //TODO (apenas a ShipCell Class e nao derivados (ShipHit e ShipSunk)"))
            var consequence = ShotConsequence.HIT
            val ship = (cell.value as ShipCell).ship // ship with position that was shot
            newGrid = grid + Pair(pos, ShipHit(pos, ship))
            val correspondentPos = newGrid.entries.filter { it.key in ship.positions() }
            if (correspondentPos.all { it.value is ShipHit }) { // if after hitting the ship all Cells are ShipHit, make them ShipSunk
                consequence = ShotConsequence.SUNK
                newGrid.entries.mapIndexed { idx, currElem ->
                    if (currElem == correspondentPos[idx])
                        Pair(currElem.key, ShipSunk(currElem.key, ship))
                }
            }
            BoardResult(copy(grid = newGrid), consequence)
        } else BoardResult(this, ShotConsequence.INVALID, PlayError.INVALID_SHOT)

    } else {
        newGrid = grid + Pair(pos, MissCell(pos))
        return BoardResult(copy(grid = newGrid), ShotConsequence.MISS)
    }
}
