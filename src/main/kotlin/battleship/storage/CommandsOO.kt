package battleship.storage

import battleship.model.Game.Game
import kotlin.system.exitProcess

/**
 * Represents a command.
 */
abstract class CommandOO {
    /**
     * Operation to be performed in the game with the indicated arguments.
     * @param game Actual game state.
     * @param args Arguments passed to command.
     * @return The game with the changes made or null if it is to end.
     */
    abstract fun action(game: Game, args: List<String>): Game?

    /**
     * Presentation of command result.
     * @param game Actual game state.
     */
    open fun show(game: Game) {}

    /**
     * Description of the syntax of the arguments
     */
    open val argsSyntax = ""
}

/**
 * Creates the associative map of game commands that associates the name of the command to its representation.
 */
fun getCommandsOO(storage: Storage) = mapOf(

        /*
"START" to object : CommandOO() {
override fun action(game: Game, args: List<String>): Game {
    require(args.isNotEmpty()) { "Missing game name" }
    return game
}

override val argsSyntax = "<gameName>"
},
"PUT" to object : CommandOO() {
override fun action(game: Game, args: List<String>) = game
override fun show(game: Game) {
    println("PUT")
}

override val argsSyntax = "<ShipType> <position> <h/v>"
},
"REFRESH" to object : CommandOO() {
override fun action(game: Game, args: List<String>) = game
override fun show(game: Game) {
    println("REFRESH")
}
},

 */
    "PUT" to object : CommandOO() {
            override fun action(game : Game, args : List<String>) = game
        override fun show(game : Game)
        {
            println(" puts in the houses ");
        }

        // TODO: averiguar se podemos por os argsSyntax do put melhor
        override val argsSyntax: String
            get() = "<shipType> <position> <align> ou <shipType> ou all"
    },
    "REMOVE" to object : CommandOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game : Game)
        {
            println("REMOVE LOL");
        }
    }
    "GRID" to object : CommandOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            println("Grid")
        }
    },
    "EXIT" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game? = null
        override fun show(game: Game) {
            exitProcess(100)
        }
    },
)
