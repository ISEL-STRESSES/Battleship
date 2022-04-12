package battleship.storage

import battleship.model.Game
import battleship.model.Player

interface Storage {
    fun start(name: String): Player
    fun store(game: Game) // store
    fun load(game: Game): Game
}
