package battleship.model.ship

import battleship.model.Cell

data class Ship(val type: ShipType, val cell: List<Cell> = emptyList())


fun Ship.makeBox() {
    TODO()
}

fun Ship.checkBox() {
    TODO()
}
