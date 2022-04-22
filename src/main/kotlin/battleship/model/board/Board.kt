package battleship.model.board

import battleship.model.Cell
import battleship.model.MissCell
import battleship.model.ShipCell
import battleship.model.ship.Ship
import battleship.model.ship.ShipType

typealias Fleet = List<Ship>
typealias Grid = Map<Position, Cell>

data class Board(val fleet: Fleet = emptyList(), val grid: Grid = mapOf())

fun Board.putShip(type : ShipType, pos : Position, direction : Direction) : Board
{
    // TODO: change exception to put result
    if(fleet.count{ type == it.type } >= type.fleetQuantity)
        throw Exception("No more ${type.name} to put")

    

    val newShip = Ship(type)
    val cells = List(type.squares) { ShipCell(pos.movePosition(direction, it), newShip) }

    val newFleet = fleet + newShip
    val newGrid = grid + cells.associateBy { it.pos }
    return copy(fleet = newFleet, newGrid);
}
