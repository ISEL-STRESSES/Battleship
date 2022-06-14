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

const val BOARD_CELL_SIZE = 32
const val BOARD_LINE_SIZE = 1
//TODO: deixar esta equacao para depois, ja que o rodrigo nao para de chatear
//TODO: Carlos Job
const val BOARD_WIDTH = (BOARD_CELL_SIZE + BOARD_LINE_SIZE) * (COLUMN_DIM + 2)
const val BOARD_HEIGHT = (BOARD_CELL_SIZE + BOARD_LINE_SIZE) * (ROW_DIM + 1)


@Composable
fun BoardView(board: Board, hidden : Boolean, onClickCell: (Position) -> Unit) {
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
                    CellView(cell, hidden, onClick = { onClickCell(pos) })
                }
            }
        }
    }
}

// TODO: should padding of boardline be in coordinate modifier?
val coordinateModifier = Modifier.size(BOARD_CELL_SIZE.dp)

@Composable
fun IdentifierView(str: String) {
    Box (contentAlignment = Alignment.Center, modifier = coordinateModifier){
        Text(textAlign = TextAlign.Center, text = str)
    }
}

@Composable
fun LetterAxisView() {
    Row {
        repeat(COLUMN_DIM) {
            val letter = it.indexToColumn().letter.toString()
            IdentifierView(letter)
            Spacer(Modifier.size(BOARD_LINE_SIZE.dp))
        }
    }
}

@Composable
fun NumberAxisView() {
    Column {
        repeat(ROW_DIM) {
            val number = it.indexToRow().number.toString();
            IdentifierView(number)
            Spacer(Modifier.size(BOARD_LINE_SIZE.dp))
        }
    }
}

@Composable
fun BoardWithGuidesView(board: Board, hidden : Boolean, onClickCell: (Position) -> Unit) {
    Column(Modifier.background(Color.White).width(BOARD_WIDTH.dp).height(BOARD_HEIGHT.dp)) {
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
            BoardView(board, hidden, onClickCell)
        }
    }
}