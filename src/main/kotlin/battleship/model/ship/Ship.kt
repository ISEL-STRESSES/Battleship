package battleship.model.ship

import battleship.model.board.Direction
import battleship.model.board.Position
import battleship.model.board.movePosition


fun Ship.positions(): List<Position> = List(type.squares) { head.movePosition(dir, it) }
