package battleship.model.ship

import battleship.model.Cell

data class Ship(val type: ShipType)

typealias ShipHitBox = List<ShipCell>

fun Ship.makeBox() {
    TODO()
}

fun Ship.checkBox() {
    TODO()
}
