import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import battleship.storage.MongoStorage
import battleship.ui.BattleshipApp
import com.mongodb.MongoException
import mongoDB.MongoDriver



fun main() {
    try {
        MongoDriver().use { drv ->
            application(exitProcessOnExit = false) {
                Window(
                    title = "Battleship",
                    onCloseRequest = ::exitApplication,
                    state = WindowState(
                        position = WindowPosition(Alignment.Center),
                        size = DpSize.Unspecified
                    )
                ) {
                    BattleshipApp(MongoStorage(drv), onExit = ::exitApplication);
                }
            }
        }
    } catch (ex: MongoException) {
        println("An exception has occurred with MongoDB!")
        println("Exception message: ${ex.message}")
    } catch (ex: Exception) {
        println("An unknown exception has occurred!")
        println("Exception message: ${ex.message}")
    } finally {
        println("Bye!")
    }
}


