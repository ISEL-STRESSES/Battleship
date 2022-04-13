package battleship.model.board

import battleship.model.Game.Player
import battleship.model.Position.COLUMN_DIM
import battleship.model.Position.Position
import battleship.model.Position.ROW_DIM
import battleship.model.Ship.Ship
import battleship.model.Ship.ShipType

const val BOARD_DIM = COLUMN_DIM * ROW_DIM

data class Move(val shot: Position, val player: Player)

data class Board(val cells: List<Cell>, /* = List(BOARD_DIM){Cell(Position(it))}*/ val moves: List<Move> = emptyList())

fun Board.addShip(shipType: ShipType, pos: Position, dir: Direction): Board {
    return if (!doesCollide(shipType, pos, dir))
        copy(// eu sei qeu da erro mas nao devia ser assim o raciocinio //fazer feature
            cells = cells + Ship(shipType, shipType.makeBox(pos, dir)).toCells())
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
