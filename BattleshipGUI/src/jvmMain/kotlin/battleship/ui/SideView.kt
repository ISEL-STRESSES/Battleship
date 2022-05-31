package battleship.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
const val XPTO = 0

@Composable
fun ShipSelectorView(fleet: Fleet, onClick: (ShipType?)->Unit, currShip: ShipType?) {
    Column(Modifier.border(BORDER_WIDTH.dp, Color.Blue).padding(10.dp), verticalArrangement = Arrangement.Center) {
        ShipType.values.forEach { type ->
            val fleetQuantity = fleet.count { it.type == type }
            //print Cells with blue boxes afterwards
            Row(verticalAlignment = Alignment.CenterVertically)  {
                val func = {
                    onClick(type)
                }
                val isSelected = type === currShip
                RadioButton(onClick = func, colors = RadioButtonDefaults.colors(Color.Cyan), selected = isSelected)
                Text("$fleetQuantity of ${type.fleetQuantity}")
                // cell representations
                repeat(type.squares) {
                    //CellView(ShipCell())
                }
            }


        }
    }
}

@Composable
fun DirectionSelectorView(onClick: (Direction)->Unit, currDir: Direction) {
    Column(Modifier.border(2.dp, Color.Blue).padding(10.dp)) {
        Direction.values().forEach {dir ->
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
fun SideView(fleet: Fleet, onClickType : (ShipType?)->Unit, currType: ShipType?, onClickDirection : (Direction)->Unit, currDir : Direction) {
    Column(verticalArrangement = Arrangement.SpaceBetween){
        ShipSelectorView(fleet, onClickType, currType)
        DirectionSelectorView(onClickDirection, currDir)
    }

}