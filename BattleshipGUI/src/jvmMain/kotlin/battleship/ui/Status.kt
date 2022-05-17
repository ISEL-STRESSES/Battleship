package battleship.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import battleship.model.board.COLUMN_DIM


@Composable
fun StatusView(state: GameState) = Row(
    Modifier.width((4.dp+CELL_SIZE.dp)* COLUMN_DIM),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    /*
    with(state.game.state) {
        Text(if (this === battleship.model.GameState.SETUP) "Game not started" else "Game: $name")
        if (this!=null) {
            if (board.winner==null)
                Text(if (player == board.turn) "Its your turn" else "wait for other")
            else
                Text("You ${if (board.winner==player) "Win" else "Lose"}")
            CellView(player,null)
        }
    }

     */
}

