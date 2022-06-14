package battleship.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import battleship.model.board.Cell
import battleship.model.board.ShipCell
import battleship.model.board.ShipHit
import battleship.model.board.ShipSunk
import battleship.model.board.MissCell

const val BOARD_CELL_SIZE = 32
val CELL_COLOR_HIDDEN = Color.Gray;
val CELL_COLOR_SUNK = Color.Black;
val CELL_COLOR_SHIP = Color.Blue;
val CELL_COLOR_WATER = Color.Cyan;

const val CELL_HIT_IMAGE = "flame.svg"
const val CELL_HIT_DESCRIPTION = "Hit ship!"

@Composable
fun CellView(cell: Cell?, hidden : Boolean, onClick: () -> Unit) {
    val modifier = Modifier.size(BOARD_CELL_SIZE.dp).background(Color.White).clickable(onClick = onClick)
    val m = modifier.background(
        if(hidden) {
            when (cell) {
                is ShipSunk -> CELL_COLOR_SUNK
                else -> CELL_COLOR_HIDDEN
            }
        } else {
            when (cell) {
                is ShipSunk -> CELL_COLOR_SUNK
                is ShipCell -> CELL_COLOR_SHIP
                null, is MissCell -> CELL_COLOR_WATER
            }
        }
    )
    Box(m) {
        when (cell) {
            //TODO por circuluzinho aqui
            is MissCell -> Image(painterResource(CELL_HIT_IMAGE), CELL_HIT_DESCRIPTION, Modifier.size(BOARD_CELL_SIZE.dp), alpha = 0.25f)
            is ShipHit -> Image(painterResource(CELL_HIT_IMAGE), CELL_HIT_DESCRIPTION, Modifier.size(BOARD_CELL_SIZE.dp))
        }
    }
}



