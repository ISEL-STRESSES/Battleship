package battleship.ui

import battleship.model.Game

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

//    "PUT" to object : CommandOO() {
//        override fun action(game: Game, args: List<String>): Game {
//
//            require(args > 0) { "Invalid arguments\nUse: $argsSyntax "}
//
//            if(args.size == 1) {
//                if(args[0] == "all"){
//                    //place all randomly
//                } else {
//                    // place specifying by carrier number
//                    val num = args[0].toInt()
//                    val type = ShipType.values[num]
//
//                }
//
//            } else if() {
//
//            }
//
//            return game.copy()
//        }
//
//        override fun show(game: Game) {
//            game.print()
//        }
//
//        // TODO: averiguar se podemos por os argsSyntax do put melhor
//        override val argsSyntax: String
//            get() = "PUT (<shipType> [<position> <align>] | all)"
//    },

    "GRID" to object : CommandOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            game.print()
        }
    },
    "HELP" to object : CommandOO() {
        override fun action(game: Game, args: List<String>) = game
        override fun show(game: Game) {
            // TODO: desculpa-me jesus pelo hardcodedness
            printHelp()
        }
    },
    "EXIT" to object : CommandOO() {
        override fun action(game: Game, args: List<String>): Game? = null
    },
)
