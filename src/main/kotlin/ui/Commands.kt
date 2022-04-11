package ui

import model.Game
import storage.Storage
import kotlin.system.exitProcess

/**
 * Represents a command.
 */
abstract class Command {
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
fun getCommands(storage: Storage) = mapOf(
    "START" to object : Command() {
        override fun action(game: Game, args: List<String>): Game {
            require(args.isNotEmpty()) { "Missing game name" }
            return game
        }

        override val argsSyntax = "<gameName>"
    },
    "PUT" to object : Command() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            println("PUT")
        }

        override val argsSyntax = "<ShipType> <position> <h/v>"
    },
    "REFRESH" to object : Command() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            println("REFRESH")
        }
    },
    "GRID" to object : Command() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            println("Grid")
        }
    },
    "EXIT" to object : Command() {
        override fun action(game: Game, args: List<String>): Game? = null
        override fun show(game: Game) {
            exitProcess(100)
        }
    },
)
