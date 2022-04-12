package battleship.model.Ship

/**
 * All ship types allowed in the game.
 * @property name battleship.model.Board.Ship.Ship type name.
 * @property squares Number of squares occupied vertically or horizontally.
 * @property fleetQuantity Number of ships of this type in the starting fleet.
 */
class ShipType private constructor(val name: String, val squares: Int, val fleetQuantity: Int) {
    companion object {
        val values = listOf(
            ShipType("Carrier", 5, 1),
            ShipType("Battleship", 4, 2),
            ShipType("Cruiser", 3, 3),
            ShipType("Submarine", 2, 4)
        )
    }
}

// TODO: averiguar se podemso reduzir linhas no toShipTypeOrNull() e toShipType()
/**
 * @brief Returns a Shiptype according to the string, if string is an integer, return a ship by number of squares
 *          else if it is a string, return the only shiptype that starts with the string as prefix, else return null
 */
fun String.toShipTypeOrNull(): ShipType? {
    val num = this.toIntOrNull()
    if (num == null) {
        val possibilities = ShipType.values.filter { it.name.startsWith(this) }
        if (possibilities.size > 1)
            throw NoSuchElementException()
        return possibilities.first()
    } else {
        return ShipType.values.firstOrNull { it.squares == num }
    }
}

/**
 * @brief Returns a Shiptype according to the string, if string is an integer, return a ship by number of squares
 *          else if it is a string, return the only shiptype that starts with the string as prefix
 * @throws NoSuchElementException
 */
fun String.toShipType(): ShipType {
    val result = this.toShipTypeOrNull()
    checkNotNull(result)
    return result
}
