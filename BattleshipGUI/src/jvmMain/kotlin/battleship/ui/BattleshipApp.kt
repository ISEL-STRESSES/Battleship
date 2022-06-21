package battleship.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.FrameWindowScope
import battleship.model.*
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

        if (model.openDialogName) {
            DialogName(onCancel = { model.closeDialog() }) {
                //TODO: improve sanitization
                val sanitizedInput = it.trim()
                model.start(sanitizedInput)
            }
            model.message?.let {
                DialogMessage(it) { model.messageAck() }
            }
        }

        Column {
            Row(modifier = Modifier.width(((BOARD_WIDTH * 2)+ BOARD_CELL_SIZE).dp).height(BOARD_HEIGHT.dp)) {
                ///////////////
                // Left Side //
                ///////////////
                val onClickCell: (Position) -> Unit = { pos ->
                    if(model.game.hasNotStarted()) {
                        val cell = model.game.playerBoard.grid[pos]
                        if (cell is ShipCell)
                            model.removeShip(pos)
                        else
                            model.putShip(pos)
                    }
                }
                BoardWithGuidesView(model.game.playerBoard, false, onClickCell)

                ////////////////
                // Right Side //
                ////////////////
                if (model.game.hasNotStarted()) {
                    val onClickShip: (ShipType?) -> Unit = { type ->
                        model.setShipType(type)
                    }
                    val onClickDir: (Direction) -> Unit = { dir ->
                        model.setDirection(dir)
                    }
                    SideView(
                        model.game.playerBoard.fleet,
                        onClickShip,
                        model.selectedType,
                        onClickDir,
                        model.selectedDirection
                    )
                } else {
                    val onClickEnemyCell: (Position) -> Unit = { pos ->
                        println("onClickEnemyCell() was called");
                        model.makeShot(pos)
                    }
                    val enemyBoard = model.getGame<GameFight>().enemyBoard
                    BoardWithGuidesView(enemyBoard, true, onClickEnemyCell)
                }
            }
            Spacer(Modifier.size(SPACER.dp))
            StatusView(model)
        }
    }
}
