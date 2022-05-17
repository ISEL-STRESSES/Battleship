import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
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
                    //TODO: remove null storage lol <- ja arranjei este TODO lol
                    BattleshipApp(MongoStorage(drv), onExit = ::exitApplication);
                }
            }
        }
    } catch (ex: MongoException) {
        println("An exception has occurred with MongoDB!")
        println("Exception message: ${ex.message}")
    } catch (ex: Exception) {
        println("An unknown exception has ocurred!")
        println("Exception message: ${ex.message}")
    } finally {
        println("Bye!")
    }
}
