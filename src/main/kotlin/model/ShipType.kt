package model

/**
 * All ship types allowed in the game.
 * @property name Ship type name.
 * @property squares Number of squares occupied vertically or horizontally.
 * @property fleetQuantity Number of ships of this type in the starting fleet.
 */
class ShipType private constructor(val name: String, val squares: Int, val fleetQuantity: Int) {
    companion object {
        val values = listOf(
            ShipType("Carrier", 5, 1), ShipType("Battleship", 4, 2),
            ShipType("Cruiser", 3, 3), ShipType("Submarine", 2, 4)
        )
    }

    override fun toString(): String {
        return when (name) {
            "Carrier" -> "A"
            "Cruiser" -> "C"
            "Battleship" -> "B"
            "Submarine" -> "S"
            else -> name
        }
    }
}

/**
 * Function that converts a string into a ShipType if the given string has a prefix of the ship or
 * number of squares matches any ShipType.
 * @receiver prefix or number of squares of a Ship.
 * @return ShipType of null.
 */
fun String.toShipTypeOrNull(): ShipType? {
    val int = this.toIntOrNull()
    if (int == null) {
        val first = ShipType.values.firstOrNull { this in it.name } ?: return null
        val last = ShipType.values.lastOrNull { this in it.name }
        return if (last !== first) null else first
    }
    val first = ShipType.values.firstOrNull { int == it.squares } ?: return null
    val last = ShipType.values.lastOrNull { int == it.squares }
    return if (last !== first) null else first
}

/**
 * Function that converts a string into a ShipType if the given string has a prefix of the ship or
 * number of squares matches any ShipType.
 * Throws NoSuchElementException if not possible.
 * @receiver prefix or number of squares of a Ship.
 * @return ShipType.
 */
fun String.toShipType(): ShipType = toShipTypeOrNull() ?: throw java.util.NoSuchElementException()
