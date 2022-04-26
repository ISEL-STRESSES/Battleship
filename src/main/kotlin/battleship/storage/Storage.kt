package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.Board



typealias FileContent = List<String>

/**
 * Basic operations for persistence of game information.
 */
interface Storage {

    /**
     * Starts a new game, if a game wasn't already available, it will create a game on storage with only boardA,
     * if boardA is already loaded it will return a playerB and save the boardB along with boardA on according storage.
     *  @param name name of the game
     *  @param board board of the player
     *  @return represents the Player the client will assume
     */
    fun start(name: String, board: Board): Player

    /**
     * Update game state in database.
     * @param game Game information to store
     */
    fun store(game: Game)

    /**
     * Loads the new game state from the information stored in the storage.
     */
    fun load(game: Game): Game
}
