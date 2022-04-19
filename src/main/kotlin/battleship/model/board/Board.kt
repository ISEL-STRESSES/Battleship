package battleship.model.board

import battleship.model.MissCell
import battleship.model.board.position.COLUMN_DIM
import battleship.model.board.position.Position
import battleship.model.board.position.ROW_DIM
import battleship.model.ship.Ship

const val BOARD_DIM = COLUMN_DIM * ROW_DIM

data class Board(val ships : List<Ship> = emptyList(), val missCells: List<MissCell> = listOf(MissCell(Position[2,3])))

/*
fun Board.addShip(shipType: ShipType, pos: Position): Board {
    return if (!doesCollide(shipType, pos))
        copy(
            cells = cells + Ship(shipType, shipType.makeBox(pos)).toCells()
        )
    else this // meter alguma flag para o user saber que o ship nao foi adicionado
}

fun ShipType.makeBox(pos: Position, dir: Direction): List<Cell> {
    TODO("Not yet implemented")
}

fun Board.doesCollide(ship: ShipType, pos: Position, dir: Direction): Boolean {
    val shipLine = cells.any { it.pos == pos } // only checks the ship line
    val box = ship.checkBox(pos, dir)
    return shipLine || box
}

fun ShipType.checkBox(pos: Position, dir: Direction): Boolean {
    TODO("Not yet implemented")
}

 */
