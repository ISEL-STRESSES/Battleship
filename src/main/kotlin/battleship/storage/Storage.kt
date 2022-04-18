package battleship.storage

import battleship.model.board.Board
import battleship.model.game.Game
import battleship.model.game.Player

interface Storage {
    fun start(name: String, board: Board): Player
    fun store(game: Game) // store
    fun load(game: Game): Game
}
