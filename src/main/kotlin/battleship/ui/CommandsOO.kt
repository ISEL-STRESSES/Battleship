package battleship.ui

import battleship.model.*
import battleship.model.board.*
import battleship.model.ship.toShipType
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
            require(args.size == 1|| args.size == 3) { "Invalid Arguments" }
            game.state.checkPutState()

            if(args.size == 1)
            {
                if(args[0] == "all")
                    return game.putAllShips();
                else {
                    val type = args[0].toShipType()
                    return game.putRandomShip(type)
                }
            } else {
                val type = args[0].toShipType()
                val position = args[1].toPosition()
                val direction = args[2].toDirection() // meter em enum class como o stor fez
                return game.putShip(type, position, direction)
            }
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
            game.state.checkPutState()

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
        override fun action(game: Game, args: List<String>): Game {
            require(args.size == 1 && args[0].isNotBlank()) { "Invalid Arguments" }
            check(game.state == GameState.SETUP) { "Game Already Started" } //TODO() put this shit in checkstate function
            check(game.boardA.fleet.isComplete()) { "Complete fleet before start" }
            val gameName = args[0]
            return game.startGame(gameName, st)
        }

        override fun show(game: Game) {
            println("You are the Player ${game.player.id}")
        }

        override val argsSyntax: String
            get() = "<name>"
    },

    "SHOT" to object : CommandsOO() {

        override fun action(game: Game, args: List<String>): Game {
            require(args.size == 1) { "Invalid Arguments" }
            check(game.state == GameState.FIGHT) { "Can't make a shot before start" }
            check(game.player == game.turn || game.boardB == null) { "Wait for other player" }

            val loadedGame = st.load(game)
            val pos = args.first().toPositionOrNull() ?: error("Invalid ${args.first()}")
            val result = loadedGame.makeShot(pos, st)
            return result.game
        }

        override fun show(game: Game) {
            game.print()
        }

        override val argsSyntax by lazy { "<position>" }
    },
    "REFRESH" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game {
            check(game.state != GameState.SETUP) { "Can't refresh an open game" }
            return st.load(game)
        }

        override fun show(game: Game) {
            game.print()
        }
    },
    "EXIT" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? = null
    },
)
