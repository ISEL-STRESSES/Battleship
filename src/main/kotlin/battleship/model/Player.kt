package battleship.model

/**
 * Players available in the game
 * @property id of the [Player]
 * @property other returns the other player
 */
enum class Player(val id: Char) {
    A('A'), B('B');
    fun other() = if (this === A) B else A
}
