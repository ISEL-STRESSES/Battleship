package battleship.model.ship

import battleship.model.ShipCell


data class Ship(val type: ShipType)

enum class HitStatus {
    HIT, SUNK
}

typealias ShipHitBox = List<ShipCell>

fun Ship.makeBox() {
    TODO()
}

fun Ship.checkBox() {
    TODO()
}
