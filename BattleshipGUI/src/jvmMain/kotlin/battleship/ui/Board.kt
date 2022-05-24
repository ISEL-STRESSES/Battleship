package battleship.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import battleship.model.board.*

const val CELL_SIZE = 32

@Composable
fun CellView(cell: Cell?) {
    val modifier = Modifier.size(CELL_SIZE.dp).background(Color.White)
    val m = modifier.background(
        when (cell) {
            is ShipSunk -> Color.Black
            is ShipHit -> Color.Gray
            is ShipCell -> listOf(Color.Yellow, Color.Green, Color.Magenta).random()
            else -> Color.Cyan
        }
    )
    Box(m) {

    }
}

/*

@Composable
fun BoardStatusView(fleet: Fleet)
{
        for (ShipType.values )
        {

        }
}

 */

const val BOARD_LINE_WIDTH = 1

@Composable
fun BoardWithGuidesView(board: Board) {
    Column(Modifier.background(Color.White)) {
        //ROW with column identifiers
        Row {
            // Inicial Spacing
            Spacer(modifier = Modifier.size(CELL_SIZE.dp))

            // Upper letters
            LetterAxisView()
        }
        //ROW with board and row identifiers
        Row {
            // Left side numbers
            NumberAxisView()
            // Board lol
            BoardView(board)
        }
    }
}