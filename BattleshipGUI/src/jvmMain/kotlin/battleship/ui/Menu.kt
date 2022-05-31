package battleship.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import battleship.model.GameState
import battleship.model.board.isComplete


@Composable
fun FrameWindowScope.GaloMenu(model: ModelView, onExit: () -> Unit, ) {
    MenuBar {
        Menu("Game") {
            Item("Start", enabled = model.game.state.hasNotStarted(), onClick = { model.start() })
            Item("Refresh", enabled = model.game.state.hasStarted(), onClick = { model.refresh() })
            Item("Exit", onClick = onExit)
        }
        Menu("Fleet") {
            //TODO: make sure put all and remove all are not enabled, and do it good :D
            Item("Put All (remaining)", enabled = model.game.state.hasNotStarted() && !model.game.boardA.fleet.isComplete() , onClick = { model.putAllRandom() })

            Item("Remove All", enabled = model.game.state.hasNotStarted() && model.game.boardA.fleet.isNotEmpty(), onClick = { model.removeAll() })
        }
    }
}

