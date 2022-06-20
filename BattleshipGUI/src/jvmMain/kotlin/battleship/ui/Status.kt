package battleship.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import battleship.model.board.COLUMN_DIM
import battleship.model.GameState


const val STATUS_BAR_HEIGHT = 48;
const val STATUS_BORDER_WIDTH = 2;
val STATUS_BORDER_COLOR = Color.Black

const val STATUS_PROMPT_SETUP = "Edit Fleet"
const val STATUS_PROMPT_FIGHT_CAN_PLAY = "It's your turn"
const val STATUS_PROMPT_FIGHT_CANT_PLAY = "Sit yo ass down, not your turn"

const val STATUS_WARN_VICTORY = "You WIN!"
const val STATUS_WARN_DEFEAT = "You LOSE! ez clap"

const val STATUS_WARN_INVALID_PUT = "Invalid put"
const val STATUS_WARN_INVALID_SHOT = "Invalid Shot"
const val STATUS_WARN_INVALID_TURN = "It's not your turn"


@Composable
fun StatusView(model: ModelView) = Row(
    Modifier.height(STATUS_BAR_HEIGHT.dp).fillMaxWidth().border(STATUS_BORDER_WIDTH.dp, STATUS_BORDER_COLOR),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    with(model.game){
        when(this)
        {
            is GameSetup -> {
                Text(STATUS_PROMPT_SETUP, fontWeight = FontWeight.Bold)
            }
            is GameFight -> {
                // Bold Game
                Text("Game:", fontWeight = FontWeight.Bold)
                Text(name)
                //val turnMsg = if(model)
                Text("")
                //Text("${state.game.name}")
            }
        }
    }

}

