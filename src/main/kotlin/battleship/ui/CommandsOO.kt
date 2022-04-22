package battleship.ui

import battleship.model.Game
import battleship.model.board.toDirectionOrNull
import battleship.model.board.toPosition
import battleship.model.putShip
import battleship.model.ship.toShipTypeOrNull

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
fun getCommandsOO() = mapOf(

    "PUT" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game {
            require( args.size == 3) { "Invalid Arguments\n Use: $argsSyntax" }

            val type = args[0].toShipTypeOrNull() ?: error("MUDAR ISTO MAIS TARDE TARDE TARDE - ordem do adolfo")
            val position = args[1].toPosition()
            val direction = args[2].toDirectionOrNull()?: error("Invalid Position") // meter em enum class como o stor fez

            val result = game.putShip(type, position, direction);

            //return result
            return result
        }

        override fun show(game: Game) {
            game.print()
        }

        // TODO: averiguar se podemos por os argsSyntax do put melhor
        override val argsSyntax: String
            get() = "(<shipType> [<position> <align>] | all)"
    },

    "GRID" to object : CommandOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            game.print()
        }
    },
    "HELP" to object : CommandOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            //NOT TODO CALOR DID THE HARDCORE
            printHelp()
        }
    },
    "SHOT" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game? {
            TODO("Not yet implemented")
        }

        override fun show(game: Game) {
            TODO("Not yet implemented")
        }

        override val argsSyntax: String
            get() = "<position>"
    },
    "REMOVE" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game? {
            TODO("Not yet implemented")
        }

        override fun show(game: Game) {
            TODO("Not yet implemented")
        }
    },
    "REFRESH" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game? {
            TODO("Not yet implemented")
        }

        override fun show(game: Game) {
            TODO("Not yet implemented")
        }
    },
    "EXIT" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game? = null
    },

)
