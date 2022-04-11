package model

import model.position.Position

class ShotTarget private constructor(val sunk: Boolean = false, val hit: Boolean = false) {
    override fun toString(): String {
        return if (!hit) "O"
        else if (!sunk) "*"
        else if (sunk) "X"
        else "#"
    }
}

class Shot(val position: Position, private val target: ShotTarget, val ship: ShipType? = null) {
    override fun toString(): String {
        return target.toString()
    }
}
