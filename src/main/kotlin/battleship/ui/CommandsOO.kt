package battleship.ui

import battleship.model.*
import battleship.model.board.isComplete
import battleship.model.board.toDirection
import battleship.model.board.toPosition
import battleship.model.ship.toShipType
import battleship.model.ship.toShipTypeOrNull
import battleship.storage.Storage

private const val ERROR_INVALID_ARGUMENTS = "Invalid Arguments"

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
            require(args.size == 1 || args.size == 3) { ERROR_INVALID_ARGUMENTS }
            check(game.state == GameState.SETUP) { "Can't change fleet after game started" }

            if (args.size == 1) {
                return if (args[0] == "all") {
                    val res = game.putAllShips()
                    if (res.second === PutConsequence.INVALID_RANDOM)
                        error("Can't place more random ships")
                    res.first
                } else {
                    val type = args[0].toShipTypeOrNull() ?: error("Ship type ${args[0]} invalid")
                    val result = game.putRandomShip(type)
                    if (result.second === PutConsequence.INVALID_RANDOM) error("Can't place ${type.name} random ship")
                    if (result.second === PutConsequence.INVALID_SHIP) error("No more ${type.name} to put")
                    result.first
                }
            } else {
                val type = args[0].toShipType()
                val position = args[1].toPosition()
                val direction = args[2].toDirection()
                val result = game.putShip(type, position, direction)

                if (result.second === PutConsequence.INVALID_SHIP) error("No more ${type.name} to put")
                if (result.second === PutConsequence.INVALID_POSITION) error("Can't put ${type.name} in that position")

                return result.first
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
            require(args.size == 1) { ERROR_INVALID_ARGUMENTS }
            check(game.state == GameState.SETUP) { "Can't change fleet after game started" }

            return if (args[0] == "all") {
                game.removeAll()
            } else {
                val position = args[0].toPosition()
                val updatedGame = game.removeShip(position)
                if (game.boardA === updatedGame.boardA) {
                    error("No ship in $position")
                }
                updatedGame
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
            require(args.size == 1 && args[0].isNotBlank()) { ERROR_INVALID_ARGUMENTS }
            check(game.state == GameState.SETUP) { "Game Already Started" }
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
            require(args.size == 1) { ERROR_INVALID_ARGUMENTS }
            check(game.state === GameState.FIGHT) { "Can't make a shot before start" }
            check(game.player === game.turn || game.boardB == null) { "Wait for other player" }

            val loadedGame = st.load(game)
            val pos = args.first().toPosition()
            val result = loadedGame.makeShot(pos, st)
            check(result.second != ShotConsequence.INVALID) { "Position already used" }

            printShotResult(result.second, result.third)

            return result.first
        }

        override fun show(game: Game) {
            game.print()
        }

        override val argsSyntax = "<position>"
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
    }
)
