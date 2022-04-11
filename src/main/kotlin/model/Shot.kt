package model

import model.position.Position

class POLICE private constructor(val finish: Boolean = false, val hit: Boolean = false) {
    override fun toString(): String {
        return if (!hit) "O"
        else if (!finish) "*"
        else if (finish) "X"
        else "#"
    }
}

class Shot(val position: Position, private val status: POLICE, val ship: ShipType? = null) {
    override fun toString(): String {
        return status.toString()
    }
}
