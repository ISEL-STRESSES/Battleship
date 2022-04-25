package battleship.storage

import battleship.model.Game
import battleship.model.Player
import battleship.model.board.Board
import java.io.File

class FileStorage : Storage {

    private fun getFile(name: String) = File("$name.txt")

    override fun start(name: String, board: Board): Player {
        val file = getFile(name)
        if (file.exists()) // player A has already begun
        {

            return Player.B
        }
        file.writeText("")

        return Player.A
    }

    override fun store(game: Game) {
        println(Player.A.toString())
        game.boardA.fleet.forEach(::print)
        println()
        game.boardA.grid.forEach(::print)
        println(Player.B.toString())
        game.boardB?.fleet?.forEach(::print)
        println()
        game.boardB?.grid?.forEach(::print)
    }

    override fun load(game: Game): Game {
        val file = getFile(game.name)
        TODO()
    }
}
