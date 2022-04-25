package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.Board

interface Storage {
    fun start(name: String, board: Board): Player
    fun store(game: Game)
    fun load(game: Game): Game
}

typealias FileContent = List<String>
