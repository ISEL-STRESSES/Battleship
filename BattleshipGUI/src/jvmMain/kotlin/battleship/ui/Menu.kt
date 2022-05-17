package battleship.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import battleship.model.board.isComplete


@Composable
fun FrameWindowScope.GaloMenu(state: GameState, onExit: () -> Unit) {
    MenuBar {
        Menu("Game") {
            Item("Start", enabled = state.hasNotStarted(), onClick = { state.start() })
            Item("Refresh", enabled = state.hasStarted(), onClick = { state.refresh() })
            Item("Exit", onClick = onExit)
        }
        Menu("Fleet") {
            //TODO: make sure put all and remove all are not enabled, and do it good :D
            Item("Put All (remaining)", enabled = state.hasNotStarted() && !state.game.boardA.fleet.isComplete() , onClick = { state.putAllRandom() })

            Item("Remove All", enabled = state.hasNotStarted() && state.game.boardA.fleet.isNotEmpty(), onClick = { state.removeAll() })
        }
    }
}

