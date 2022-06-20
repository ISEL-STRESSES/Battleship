package battleship.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import battleship.model.GameState
import battleship.model.board.isComplete
import battleship.model.hasNotStarted
import battleship.model.hasStarted


@Composable
fun FrameWindowScope.GaloMenu(model: ModelView, onExit: () -> Unit) {
    MenuBar {
        Menu("Game") {
            Item("Start", enabled = model.game.hasNotStarted() && model.game.playerBoard.fleet.isComplete(), onClick = { model.start() })
            Item("Refresh", enabled = model.game.hasStarted(), onClick = { model.refresh() })
            Item("Exit", onClick = onExit)
        }
        Menu("Fleet") {
            Item("Put All (remaining)", enabled = model.game.hasNotStarted() && !model.game.playerBoard.fleet.isComplete() , onClick = { model.putAllRandom() })

            Item("Remove All", enabled = model.game.hasNotStarted() && model.game.playerBoard.fleet.isNotEmpty(), onClick = { model.removeAll() })
        }
    }
}

