package storage

import model.Game
import model.Player

interface Storage {
    fun start(name: String): Player
    fun store(game: Game) // store
    fun load(game: Game): Game
}
