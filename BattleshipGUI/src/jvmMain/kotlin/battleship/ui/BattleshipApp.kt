package battleship.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.FrameWindowScope
import battleship.model.ship.ShipType
import battleship.storage.Storage


@Composable
fun FrameWindowScope.BattleshipApp(storage: Storage, onExit: () -> Unit) {
    val state = remember { GameState(storage) } // State of UI (ViewModel)
    MaterialTheme {
        GaloMenu(state, onExit = onExit)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                BoardWithGuidesView(
                    state.game.boardA
                )
                if(state.game.state === battleship.model.GameState.SETUP)
                {
                    val onClickShip : (ShipType?)->Unit = {
                        state.setShipType(it)
                    }
                    SideView(state.game.boardA.fleet, onClickShip, state.selectedType)
                } else {
                    state.game.boardB?.let {
                        BoardWithGuidesView(it)
                    }
                }
            }

            StatusView(state)
        }
    }
}
