package battleship.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import battleship.model.board.Direction
import battleship.model.board.Fleet
import battleship.model.ship.ShipType


const val BORDER_WIDTH = 2
const val PADDING = 10

@Composable
fun ShipSelectorView(fleet: Fleet, onClick: (ShipType) -> Unit, currShip: ShipType?) {
    Column(Modifier.border(BORDER_WIDTH.dp, Color.Blue).padding(PADDING.dp), verticalArrangement = Arrangement.Center) {
        ShipType.values.forEach { type ->
            val fleetQuantity = fleet.count { it.type == type }
            //print Cells with blue boxes afterwards
            Row(verticalAlignment = Alignment.CenterVertically) {
                val func = {
                    onClick(type)
                }
                val isSelected = type === currShip
                RadioButton(selected = isSelected, onClick = func, enabled = fleetQuantity < type.fleetQuantity, colors = RadioButtonDefaults.colors(Color.Cyan))
                Text("$fleetQuantity of ${type.fleetQuantity}")
                repeat(type.squares) {
                    Spacer(Modifier.size(1.dp))
                    val modifier = Modifier.size(24.dp)
                    val m = modifier.background(CELL_COLOR_SHIP)
                    Box(m)
                }
            }
        }
    }
}

@Composable
fun DirectionSelectorView(onClick: (Direction) -> Unit, currDir: Direction) {
    Column(Modifier.border(BORDER_WIDTH.dp, Color.Blue).padding(PADDING.dp)) {
        Direction.values().forEach { dir ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                val func = { onClick(dir) }
                val isSelected = dir === currDir
                RadioButton(onClick = func, colors = RadioButtonDefaults.colors(Color.Cyan), selected = isSelected)
                Text(dir.name)
            }
        }
    }

}

@Composable
fun SideView(
    fleet: Fleet,
    onClickType: (ShipType) -> Unit,
    currType: ShipType?,
    onClickDirection: (Direction) -> Unit,
    currDir: Direction
) {
    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween) {
        Spacer(Modifier.size(BOARD_CELL_SIZE.dp))
        ShipSelectorView(fleet, onClickType, currType)

        Spacer(Modifier.padding(BORDER_WIDTH.dp))
        DirectionSelectorView(onClickDirection, currDir)
    }

}