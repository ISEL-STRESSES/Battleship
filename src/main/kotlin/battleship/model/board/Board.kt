package battleship.model.board

import battleship.model.Cell
import battleship.model.Game
import battleship.model.Player
import battleship.model.ShipCell
import battleship.model.ship.Ship
import battleship.model.ship.ShipType
import battleship.model.ship.positions
import kotlin.math.max
import kotlin.math.min

typealias Fleet = List<Ship>
typealias Grid = Map<Position, Cell>
typealias Coords = Pair<Int, Int> //Pair<X, Y>

data class Board(val fleet: Fleet = emptyList(), val grid: Grid = mapOf())

data class Bounds(val topLeft: Coords, val bottomRight: Coords);


fun getBounds(pos: Position, dir: Direction, size: Int): Bounds {
    val extension = Pair(if (dir == Direction.HORIZONTAL) size else 0, if (dir == Direction.VERTICAL) size else 0);
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

    if (direction == Direction.HORIZONTAL){
        if (pos.column.ordinal + type.squares > COLUMN_DIM) error("Can't put ${type.name} in that position")
    } else
        if (pos.row.ordinal + type.squares > ROW_DIM) error("Can't put ${type.name} in that position");

    // get bounds of ship
    val bounds = getBounds(pos, direction, type.squares);

    //iterate through cells to see if all these cells are empty otherwise throw error
    for (y in bounds.topLeft.second until bounds.bottomRight.second) {
        for (x in bounds.topLeft.first until bounds.bottomRight.first) {
            if (this.grid[Position[x, y]] != null) error("Can't put ${type.name} in that position");
        }
    }


    val newShip = Ship(type, pos, direction)
    val cells = newShip.positions().map { currPos -> ShipCell(currPos, newShip) }

    val newFleet = fleet + newShip
    val newGrid = grid + cells.associateBy { it.pos }
    return copy(fleet = newFleet, grid = newGrid)
}

fun Board.removeShip(pos: Position): Board {
    // get cell at position
    val cell = this.grid[pos]
    return if (cell is ShipCell) {
        val ship = cell.ship
        val newFleet = fleet - ship;
        val newGrid = grid - ship.positions();

        Board(newFleet, newGrid)
    } else {
        this;
    }
}

fun Game.enemyBoard(): Board = if (player === Player.A) boardA else boardB!!


