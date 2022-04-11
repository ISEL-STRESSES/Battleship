import model.Game
import mongoDB.MongoDriver
import storage.MongoStorage
import ui.readCommand

fun main() {
    MongoDriver().use { drv ->
        var game: Game? = null
        val cmds = getCommands(MongoStorage(drv))
        var started = false
        while (true) {
            val (name, args) = readCommand()
            val cmd = cmds[name]
            if (cmd == null) println("Invalid Command $name")
            else if (!started) // If game hasn't been inicialized

                if (name == "START")
                    println("Invalid Command $name")
                else try {
                    game = game?.let { cmd.action(it, args) } ?: break
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
