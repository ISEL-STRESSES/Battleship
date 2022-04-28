package battleship.model.board

import battleship.model.Game
import battleship.model.PlayError
import battleship.model.board.ShotConsequence.*
import battleship.model.ship.Ship
import battleship.model.ship.ShipType
import kotlin.math.max
import kotlin.math.min

typealias Fleet = List<Ship>
typealias Grid = Map<Position, Cell>
typealias Coords = Pair<Int, Int> // TODO(Convert to Position)

/**
 * Board of each player
 * @property fleet [Fleet] of the player, starting as an empty list
 * @property grid [Grid] of the player, starting as an empty map
 */
data class Board(val fleet: Fleet = emptyList(), val grid: Grid = mapOf())

/**
 * Returns the number of [ShipCell] remaining (where a ship that has not been sunked is)
 * @return number of [ShipCell] cells
 */
fun Board.cellsQuantity(): Int {
    return this.fleet.fold(0) { acc, ship -> acc + ship.type.squares }
}

/**
 * [Board] Function that represents if a player has lost
 * @return Boolean
 */
fun Board.lost() = (cellsQuantity() - grid.entries.count { (_, it) -> it is ShipSunk }) <= 0

/**
 * Class with the ends of each Ship's BlackBox
 * @property topLeft [Coords] of the top left position
 * @property bottomRight [Coords] of the bottom right position
 */
data class Bounds(val topLeft: Coords, val bottomRight: Coords)

/**
 * Function that will create the bounds of a ship
 * @param pos [Position] of the head of the ship
 * @param dir [Direction] of the ship
 * @param size size of the ship
 * @return [Bounds] of the ship
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

enum class PutError { NONE, INVALID_POSITION, INVALID_ARGUMENTS }
data class PutResult(val game: Game, val error: PutError)
/**
 * [Board] Function that will put a ship
 * @param type [ShipType] of the ship to put
 * @param pos head [Position] of the ship
 * @param dir [Direction] of the ship
 * @return updated [Board]
 * @throws IllegalStateException
 */
fun Board.putShip(type: ShipType, pos: Position, dir: Direction): Board {

    // TODO() change return to PutResult just like the shot function
    if (fleet.count { type == it.type } >= type.fleetQuantity)
         error("No more ${type.name} to put")

    if (dir == Direction.HORIZONTAL) {
        if (pos.column.ordinal + type.squares > COLUMN_DIM) error("Can't put ${type.name} in that position")
    } else if (pos.row.ordinal + type.squares > ROW_DIM) error("Can't put ${type.name} in that position")

    // get bounds of ship
    val bounds = getBounds(pos, dir, type.squares)

    // iterate through cells to see if all these cells are empty otherwise throw can't put ship
    for (y in bounds.topLeft.second until bounds.bottomRight.second) {
        for (x in bounds.topLeft.first until bounds.bottomRight.first) {
            if (this.grid[Position[x, y]] != null) error("Can't put ${type.name} in that position")
        }
    }

    val newShip = Ship(type, pos, dir, List(type.squares) { pos.movePosition(dir, it) })
    val cells = newShip.positions.map { currPos -> ShipCell(currPos, newShip) }

    val newFleet = fleet + newShip
    val newGrid = grid + cells.associateBy { it.pos }
    return Board(newFleet, newGrid)
}

/**
 * [Board] Function that will remove a ship
 * @param pos [Position] to remove the ship from
 * @return updated [Board]
 */
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


/**
 * Class that represents the consequence of the shot taken
 * @property MISS shot was missed
 * @property HIT shot was hit
 * @property SUNK shot has sunken a ship
 * @property INVALID shot was invalid
 */
enum class ShotConsequence {
    MISS, HIT, SUNK, INVALID
}

/**
 * Keeps the board, the correspondent consequence of a shot, and the error(if there is one) associated with that shot.
 * @property board [Board] of the player to be shot;
 * @property consequence [ShotConsequence] consequence of the shot taken;
 * @property error [PlayError] error associated.
 */
data class ShotResult(val board: Board, val consequence: ShotConsequence)

/**
 * [Board] Function that will make a shot.
 * @param pos [Position] from the shot.
 * @return [ShotResult] with the updated [Game], [ShotConsequence] and [PlayError] associated.
 */
fun Board.makeShot(pos: Position): ShotResult {
    when (val cell = grid[pos]) {
        is MissCell, is ShipHit -> {
            // Ship sunk is inferred as an invalid shot because of its inherited from ShipHit
            return ShotResult(this, ShotConsequence.INVALID)
        }
        is ShipCell -> {
            val ship = cell.ship
            val gridAfterShot = grid + (pos to ShipHit(pos, ship))

            val hasSunk = ship.positions.all { gridAfterShot[it] is ShipHit }
            // check if ship is sunk
            return if (hasSunk) {
                val gridAfterSunk = grid + ship.positions.map { it to ShipSunk(it, ship) }
                ShotResult(copy(grid = gridAfterSunk), ShotConsequence.SUNK)
            } else {
                return ShotResult(copy(grid = gridAfterShot), ShotConsequence.HIT)
            }
        }
        null -> {
            // Add cell to the grid with MissCell
            return ShotResult(copy(grid = grid + (pos to MissCell(pos))), ShotConsequence.MISS)
        }
    }
    return ShotResult(this, ShotConsequence.INVALID)
}

/**
 * Function that will check it fleet is complete.
 * @return true if the fleet is full else false
 */
fun Fleet.isComplete() =
    isNotEmpty() // TODO(DON'T FUCKING FORGET TO REMOVE) size == ShipType.values.sumOf { it.fleetQuantity }
