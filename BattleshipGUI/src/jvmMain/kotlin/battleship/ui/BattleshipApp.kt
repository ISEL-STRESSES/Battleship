package battleship.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.FrameWindowScope
import battleship.storage.Storage


@Composable
fun FrameWindowScope.BattleshipApp(storage: Storage, onExit: () -> Unit) {
    val state = remember { GameState(storage) } // State of UI (ViewModel)
    MaterialTheme {
        GaloMenu(state, onExit = onExit)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BoardView(
                state.game.boardA
            )
            StatusView(state)
        }
    }
}
