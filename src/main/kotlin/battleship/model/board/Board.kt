package battleship.model.board

import battleship.model.MissCell
import battleship.model.board.position.COLUMN_DIM
import battleship.model.board.position.Position
import battleship.model.board.position.ROW_DIM
import battleship.model.ship.Ship

typealias Fleet = List<Ship>
typealias Grid = List<MissCell>

data class Board(val fleet: Fleet = emptyList(), val grid: Grid = emptyList())

/*
fun Board.addShip(shipType: ShipType, pos: Position): Board {
    return if (!doesCollide(shipType, pos))
        copy(
            cells = cells + Ship(shipType, shipType.makeBox(pos)).toCells()
        )
    else this // meter alguma flag para o user saber que o ship nao foi adicionado
}

fun ShipType.makeBox(pos: Position): List<Cell> {
    TODO("Not yet implemented")
}

fun Board.doesCollide(ship: ShipType, pos: Position): Boolean {
    val shipLine = cells.any { it.pos == pos } // only checks the ship line
    val box = ship.checkBox(pos)
    return shipLine || box
}

 */
