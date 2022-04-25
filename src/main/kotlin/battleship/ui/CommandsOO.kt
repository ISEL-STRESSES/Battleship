package battleship.ui

import battleship.model.*
import battleship.model.board.toDirection
import battleship.model.board.toPosition
import battleship.model.board.toPositionOrNull
import battleship.model.ship.toShipTypeOrNull
import battleship.storage.Storage

/**
 * Represents a command.
 */
abstract class CommandsOO {
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
fun getCommandsOO(st: Storage) = mapOf(

    "HELP" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            printHelp()
        }
    },
    "PUT" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game {
            require(args.size == 3) { "Invalid Arguments" }
            game.state.checkState(GameState.SETUP) // Verify you're in the right state for put command


            val type = args[0].toShipTypeOrNull() ?: error("MUDAR ISTO MAIS TARDE TARDE TARDE - ordem do adolfo")
            val position = args[1].toPosition()
            val direction = args[2].toDirection() // meter em enum class como o stor fez

            val result = game.putShip(type, position, direction)
            // return result
            return result
        }

        override fun show(game: Game) {
            game.print()
        }

        override val argsSyntax: String
            get() = "(<shipType> [<position> <align>] | all)"
    },
    "REMOVE" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game {
            require(args.size == 1) { "Invalid Arguments" }
            game.state.checkState(GameState.SETUP) // Verify you're in the right state for put command


            return if (args[0] == "all") {
                game.removeAll()
            } else {
                val position = args[0].toPosition()
                game.removeShip(position)
            }
        }

        override fun show(game: Game) {
            game.print()
        }

        override val argsSyntax: String
            get() = "<position> | all"
    },
    "GRID" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            game.print()
        }
    },
    "START" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? {
            require(args.size == 1) { "Invalid Arguments" }
            require(args[0].isNotBlank()) { }
            val gameName = args[0]
            val res = game.startGame(gameName, st)
            return game
        }

        override fun show(game: Game) {
            game.print()
        }
    },

    "SHOT" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game {

            require(args.size == 1) { "Invalid Arguments\nUse: $argsSyntax" }
            game.state.checkState(GameState.FIGHT)
            val pos = args.first().toPositionOrNull() ?: error("Invalid $argsSyntax")
            val result = game.makeShot(pos)
            //TODO FAZER O TRATAMENTO DOS ERROS E CONSEQUENCIAS DO SHOT

            val getGameStatus = result.game.checkWin()
            /* not good board still giving me a Headache (my board?) */
            return result.game.copy(state = getGameStatus, turn = game.player.other()) // TODO("Not yet implemented")
        }

        override fun show(game: Game) {
            game.print()
            TODO("Not yet implemented")
        }

        override val argsSyntax by lazy { "<position>" }
    },
    "REFRESH" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? {
            // tem de ir buscar o novo estado da db
            // dar print a board
            // retorno nao devia ser UNIT?????
            TODO("Not yet implemented")
        }

        override fun show(game: Game) {
            game.print()
            TODO("Not yet implemented")
        }
    },
    "EXIT" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? = null
    },
)
