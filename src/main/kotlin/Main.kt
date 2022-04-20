import battleship.model.Game
import battleship.model.createGame
import battleship.ui.getCommandsOO
import battleship.ui.readCommand
import kotlin.system.exitProcess

fun main() {
    try {
        // MongoDriver("PVV").use { drv ->
        var game: Game = createGame()
        val cmds = getCommandsOO()
        while (true) {
            val (name, args) = readCommand()
            val cmd = cmds[name]
            if (cmd == null) {
                println("Invalid Command!")
            } else {
                try {
                    game = cmd.action(game, args) ?: exitProcess(0)
                    cmd.show(game)
                } catch (ex: IllegalStateException) {
                    println(ex.message)
                } catch (ex: IllegalArgumentException) {
                    println(ex.message)
                    println("Use: ${cmd.argsSyntax}")
                }
            }
        }
    }
    // TODO: Specify for mongoDB specific exceptions
    catch (ex: Exception) {
        println("An exception has occurred")
        println("Exception message: ${ex.message}")
    } finally {
        println("OS 4 F's do grupo 1:\n Força\n Foco\n Fé\n FODA-SE")
    }
}
