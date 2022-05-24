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
import java.awt.Point

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

const val BOARD_LINE = 1

@Composable
fun BoardView(board: Board) {
    Column {
        repeat(ROW_DIM) { line ->
            if (line != 0) Spacer(Modifier.height(BOARD_LINE.dp))
            Row {
                // Board
                repeat(COLUMN_DIM) { col ->
                    if (col != 0) Spacer(Modifier.width(BOARD_LINE.dp))
                    /*
            val pos = Position(line, col)
            val fx: () -> Unit = { onClick?.invoke(pos) }
             */
                    val cell = board.grid[Position[col, line]]
                    CellView(cell)
                }
            }
        }
    }
}

// TODO: should padding of boardline be in coordinate modifier?
val coordinateModifier = Modifier.size(CELL_SIZE.dp)

@Composable
fun PointView(str: String) {
    Box (contentAlignment = Alignment.Center, modifier = coordinateModifier){
        Text(textAlign = TextAlign.Center, text = str)
    }
}

@Composable
fun LetterAxisView() {
    Row {
        repeat(COLUMN_DIM) {
            val letter = it.indexToColumn().letter.toString()
            PointView(letter)
            Spacer(Modifier.size(BOARD_LINE.dp))
        }
    }
}

@Composable
fun NumberAxisView() {
    Column {
        repeat(ROW_DIM) {
            val number = it.indexToRow().number.toString();
            PointView(number)

            Spacer(Modifier.size(BOARD_LINE.dp))
        }
    }
}

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