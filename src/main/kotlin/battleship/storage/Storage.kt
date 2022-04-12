package battleship.storage

import battleship.model.Game.Game
import battleship.model.Game.Player

interface Storage {
    fun start(name: String): Player
    fun store(game: Game) // store
    fun load(game: Game): Game
}
