package battleship.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import battleship.model.GameState
import battleship.model.board.Direction
import battleship.model.board.Position
import battleship.model.board.ShipCell
import battleship.model.ship.ShipType
import battleship.storage.Storage
import tds.galo.ui.DialogName

private const val SPACER = 5

@Composable
fun FrameWindowScope.BattleshipApp(storage: Storage, onExit: () -> Unit) {
    val scope = rememberCoroutineScope()
    val model = remember { ModelView(storage, scope) }
    MaterialTheme {
        GaloMenu(model, onExit = onExit)

        if(model.openDialogName) {
            DialogName(onCancel = { model.closeDialog() }) { model.start(it) }
            model.message?.let {
                DialogMessage(it) { model.messageAck() }
            }
        }

        Column() {
            Row(modifier = Modifier.width((BOARD_WIDTH*2).dp).height(BOARD_HEIGHT.dp)) {
                val onClickCell : (Position) -> Unit = { pos ->
                    val cell = model.game.boardA.grid[pos]
                    if(cell is ShipCell)
                        model.removeShip(pos)
                    else
                        model.putShip(pos)
                }

                // Left Side aka first Board
                BoardWithGuidesView(model.game.boardA, onClickCell)

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
                        val onClickEnemyCell : (Position) -> Unit = { pos ->
                            println(pos.toString())
                        }
                        BoardWithGuidesView(it, onClickEnemyCell)
                    }

                }
            }
            Spacer(Modifier.size(SPACER.dp))
            StatusView(model)
        }
    }
}
