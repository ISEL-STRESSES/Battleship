package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.Board
import java.io.File

/**
 * TODO
 */
class FileStorage : Storage {

    /**
     * TODO
     */
    private fun getFile(name: String) = File("$name.txt")

    /**
     * TODO
     */
    override fun start(name: String, board: Board): Player {
        TODO("Not yet Implemented")
    }

    /**
     * TODO
     */
    override fun store(game: Game) {
        TODO("Not yet Implemented")
    }

    /**
     * TODO
     */
    override fun load(game: Game): Game {
        TODO("Not yet Implemented")
    }
}
