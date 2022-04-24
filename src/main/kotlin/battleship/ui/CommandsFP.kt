package battleship.ui//package battleship.ui
//
//import battleship.model.Game
//
//
///**
// * Represents a command.
// */
//class CommandFP(
//    /**
//     * Operation to be performed in the game with the indicated arguments.
//     * @param game Actual game state.
//     * @param args Arguments passed to command.
//     * @return The game with the changes made or null if it is to end.
//     */
//    val action: (game: Game?, args:List<String>) -> Game?,
//
//    /**
//     * Presentation of command result.
//     * @param game Actual game state.
//     */
//    val show: () -> Unit //TODO STILL MISSING
//
//    /**
//     * Description of the syntax of the arguments
//     */
//    val argsSyntax: String = ""
//)
//
///**
// * Creates the associative map of game commands that associates the name of the command to its representation.
// */
//fun getCommandsFP() = mapOf(
//    "GRID" to CommandFP(
//        action = { game, _ ->
//            checkNotNull(game) { "Game not started yet" }
//            game
//        }
//    ),
//    "PUT" to CommandFP(
//        action = { game, args -> //TODO to be yet implemented }
//        argsSyntax = "PUT (<shipType> [<position> <align>] | all)"
//    ),
//    "EXIT" to CommandFP(
//        action = { _, _ -> null }
//    ),
//    "START" to CommandFP(
//        action = { game, args -> startAction(game, args, st) },
//        argsSyntax = "<gameName>"
//    ),
//    "REFRESH" to CommandFP(
//        action =  { game, _ ->
//            checkNotNull(game) { "Game not started yet" }
//            st.load(game)
//        }
//    )
//)