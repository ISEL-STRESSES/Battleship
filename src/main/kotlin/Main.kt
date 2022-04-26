import battleship.model.Game
import battleship.model.createGame
import battleship.storage.FileStorage
import battleship.ui.getCommandsOO
import battleship.ui.readCommand
import mongoDB.MongoDriver
import kotlin.system.exitProcess

fun main() {
    try {
        MongoDriver("PVV").use { drv ->
            var game: Game = createGame()
            val cmds = getCommandsOO(FileStorage())
            while (true) {
                val (name, args) = readCommand()
                val cmd = cmds[name]
                if (cmd == null) {
                    println("Invalid Command! \nUse the Command HELP for the available commands list")
                } else {
                    try {
                        game = cmd.action(game, args) ?: exitProcess(0)
                        cmd.show(game)
                    } catch (ex: IllegalStateException) {
                        println(ex.message)
                    } catch (ex: IllegalArgumentException) {
                        println(ex.message)
                        println("Use: $name ${cmd.argsSyntax}")
                    }
                }
            }
        }
    }
    // TODO: Specify for mongoDB specific exceptions
    catch (ex: Exception) {
        println("An exception has occurred")
        println("Exception message: ${ex.message}")
    } finally {
        println("OS 4 F's do Grupo 1:\nForça\nFoco\nFé\nF***-**")
    }
}
