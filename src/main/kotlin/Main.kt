import model.Game
import storage.MongoStorage
import ui.getCommands
import ui.readCommand

fun main() {
    MongoDriver().use { drv ->
        var game: Game? = null
        val cmds = getCommands(MongoStorage(drv))
        while (true) {
            val (name, args) = readCommand()
            val cmd = cmds[name]
            if (cmd == null) println("Invalid Command $name")
            else try {
                game = cmd.action(game, args) ?: break
                cmd.show(game)
            } catch (ex: Exception) {
                println(ex.message)
                if (ex is IllegalArgumentException)
                    println("Use: $name ${cmd.argsSyntax}")
            }
        }
    }
    println("BYE.")
}
