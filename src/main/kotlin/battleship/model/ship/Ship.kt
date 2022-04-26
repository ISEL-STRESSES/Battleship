package battleship.model.ship

import battleship.model.board.Direction
import battleship.model.board.Position

data class Ship(val type: ShipType, val head: Position, val dir: Direction, val positions: List<Position>)
