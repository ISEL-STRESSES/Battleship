package battleship.model.ship

import battleship.model.board.Cell

data class Ship(val type: ShipType, val cells: List<Cell>)
