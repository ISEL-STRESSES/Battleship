import battleship.model.Game
import battleship.model.createGame
import battleship.ui.getCommandsOO
import battleship.ui.readCommand
import kotlin.system.exitProcess

fun main() {
    try {
        //MongoDriver().use { drv ->
        var game: Game = createGame()
        val cmds = getCommandsOO()
        while (true) {
            val (name, args) = readCommand()
            val cmd = cmds[name]
            if (cmd == null) {
                println("Invalid Command!")
            } else {
                game = cmd.action(game, args) ?: exitProcess(0)
                cmd.show(game)
            }
        }
    }
    // TODO: Specify for mongoDB specific exceptions
    catch (ex: Exception) {
        println("An exception has ocurred")
        println(ex.stackTrace)
        println(ex.message)

    } finally {
        println("1st phase done!")
    }
}
