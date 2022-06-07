package battleship.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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


const val BOARD_CELL_SIZE = 32
const val BOARD_LINE_SIZE = 1
const val BOARD_WIDTH = (BOARD_CELL_SIZE + BOARD_LINE_SIZE) * (COLUMN_DIM + 1)
const val BOARD_HEIGHT = (BOARD_CELL_SIZE + BOARD_LINE_SIZE) * (ROW_DIM + 1)

val CELL_COLOR_SUNK = Color.Black;
val CELL_COLOR_HIT = Color.Black;
val CELL_COLOR_SHIP = Color(255, 140, 0); //Color.Blue
val CELL_COLOR_WATER = Color.Cyan;

@Composable
fun CellView(cell: Cell?, onClick: () -> Unit) {
    val modifier = Modifier.size(BOARD_CELL_SIZE.dp).background(Color.White).clickable(onClick = onClick)
    val m = modifier.background(
        when (cell) {
            is ShipSunk -> CELL_COLOR_SUNK
            is ShipHit -> CELL_COLOR_HIT
            is ShipCell -> CELL_COLOR_SHIP
            else -> CELL_COLOR_WATER
        }
    )
    Box(m) {
        // TODO: add sprite in case cell is on fire kachow
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


@Composable
fun BoardView(board: Board, hidden : Boolean = false, onClickCell: (Position) -> Unit) {
    Column {
        repeat(ROW_DIM) { line ->
            if (line != 0) Spacer(Modifier.height(BOARD_LINE_SIZE.dp))
            Row {
                // Board
                repeat(COLUMN_DIM) { col ->
                    if (col != 0) Spacer(Modifier.width(BOARD_LINE_SIZE.dp))
                    /*
            val pos = Position(line, col)
            val fx: () -> Unit = { onClick?.invoke(pos) }
             */
                    val pos = Position[col, line];
                    val cell = board.grid[pos]
                    CellView(cell, onClick = { onClickCell(pos) })
                }
            }
        }
    }
}

// TODO: should padding of boardline be in coordinate modifier?
val coordinateModifier = Modifier.size(BOARD_CELL_SIZE.dp)

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
            Spacer(Modifier.size(BOARD_LINE_SIZE.dp))
        }
    }
}

@Composable
fun NumberAxisView() {
    Column {
        repeat(ROW_DIM) {
            val number = it.indexToRow().number.toString();
            PointView(number)

            Spacer(Modifier.size(BOARD_LINE_SIZE.dp))
        }
    }
}

@Composable
fun BoardWithGuidesView(board: Board,/* hidden : Boolean  ,*/onClickCell: (Position) -> Unit) {
    Column(Modifier.background(Color.White)) {
        //ROW with column identifiers
        Row {
            // Initial Spacing
            Spacer(modifier = Modifier.size(BOARD_CELL_SIZE.dp))

            // Upper letters
            LetterAxisView()
        }
        //ROW with board and row identifiers
        Row {
            // Left side numbers
            NumberAxisView()
            // Board 
            BoardView(board) { onClickCell }
        }
    }
}