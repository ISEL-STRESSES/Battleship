package battleship.ui

import battleship.model.*
import battleship.model.board.enemyBoard
import battleship.model.board.toDirection
import battleship.model.board.toPosition
import battleship.model.board.toPositionOrNull
import battleship.model.ship.toShipTypeOrNull

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
fun getCommandsOO() = mapOf(

    "HELP" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            //NOT TODO CALOR DID THE HARDCORE
            printHelp()
        }
    },
    "PUT" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game {
            require(args.size == 3) { "Invalid Arguments\n Use: $argsSyntax" }

            val type = args[0].toShipTypeOrNull() ?: error("MUDAR ISTO MAIS TARDE TARDE TARDE - ordem do adolfo")
            val position = args[1].toPosition()
            val direction = args[2].toDirection() // meter em enum class como o stor fez

            val result = game.putShip(type, position, direction)

            //return result
            return result
        }

        override fun show(game: Game) {
            game.print()
        }

        override val argsSyntax: String
            get() = "(<shipType> [<position> <align>] | all)"
    },
    "REMOVE" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? {
            require(args.size == 1) { "Invalid Arguments\n Use: $argsSyntax" }

            return if(args[0] == "all")
            {
                game.removeAll()
            } else {
                val position = args[0].toPosition()
                game.removeShip(position)
            }
        }

        override fun show(game: Game) {
            game.print()
        }
    },
    "GRID" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            game.print()
        }
    },
    "START" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? {
            TODO("Not yet implemented")
        }

        override fun show(game: Game) {
            TODO("Not yet implemented")
        }
    },
    "SHOT" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? {

            require(args.size == 1) { "Invalid Arguments\n Use: $argsSyntax" }
            checkNotNull(game.boardB) { "Game not Started" }

            val pos = args.first().toPositionOrNull() ?: error("Invalid $argsSyntax")
            val aim = game.getTarget(pos, game.enemyBoard())
            val getGameStatus = game.checkWin()
            return game.copy(
                state = getGameStatus,
                turn = game.player.other()
            ) /* not good board still giving me a Headache (my board?) */
            TODO("Not yet implemented")
        }


        override fun show(game: Game) {
            game.print()
            TODO("Not yet implemented")
        }

        override val argsSyntax: String
            get() = "<position>"
    },
    "REFRESH" to object : CommandsOO() {
        override fun action(game: Game, args: List<String>): Game? {
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
