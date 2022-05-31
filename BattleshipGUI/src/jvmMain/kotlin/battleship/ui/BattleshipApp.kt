package battleship.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.FrameWindowScope
import battleship.model.GameState
import battleship.model.board.Direction
import battleship.model.board.Position
import battleship.model.board.ShipCell
import battleship.model.ship.ShipType
import battleship.storage.Storage


@Composable
fun FrameWindowScope.BattleshipApp(storage: Storage, onExit: () -> Unit) {
    val model = remember { ModelView(storage) }
    MaterialTheme {
        GaloMenu(model, onExit = onExit)

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                val onClickCell : (Position) -> Unit = { pos ->
                    val cell = model.game.boardA.grid[pos]
                    if(cell is ShipCell)
                        model.removeShip(pos);
                    else
                        model.putShip(pos)
                }

                // Left Side aka first Board
                BoardWithGuidesView(model.game.boardA,onClickCell)

                // RightSide aka SideView/other Board
                if(model.game.state === GameState.SETUP)
                {
                    val onClickShip : (ShipType?)->Unit = { type->
                        model.setShipType(type)
                    }
                    val onClickDir : (Direction)->Unit = { dir->
                        model.setDirection(dir)
                    }
                    SideView(model.game.boardA.fleet, onClickShip, model.selectedType, onClickDir, model.selectedDirection)
                } else {
                    model.game.boardB?.let {
                        //TODO: BoardWithGuidesView(it)
                    }
                }
            }

            StatusView(model)
        }
    }
}
