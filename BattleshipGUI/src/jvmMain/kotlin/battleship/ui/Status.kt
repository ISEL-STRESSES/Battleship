package battleship.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import battleship.model.GameFight
import battleship.model.GameSetup
import battleship.model.isYourTurn

const val STATUS_BAR_HEIGHT = 48
const val STATUS_BORDER_WIDTH = 2
val STATUS_BORDER_COLOR = Color.Black

const val STATUS_PROMPT_SETUP = "Edit Fleet"
const val STATUS_PROMPT_FIGHT_PLAY_ALLOWED = "It's your turn"
const val STATUS_PROMPT_FIGHT_PLAY_NOT_ALLOWED = "Sit yo a** down, not your turn"

@Composable
fun StatusView(model: ModelView, message: String? = null) = Row(
    Modifier.height(STATUS_BAR_HEIGHT.dp).fillMaxWidth().border(STATUS_BORDER_WIDTH.dp, STATUS_BORDER_COLOR),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
) {
    with(model.game) {
        when (this) {
            is GameSetup -> {
                Text(STATUS_PROMPT_SETUP, fontWeight = FontWeight.Bold)
            }
            is GameFight -> {
                // Bold Game
                Box {
                    Text("Game: ", fontWeight = FontWeight.Bold)
                    Text(name)
                }
                val turnMsg = if (this.isYourTurn())
                        STATUS_PROMPT_FIGHT_PLAY_ALLOWED
                    else
                        STATUS_PROMPT_FIGHT_PLAY_NOT_ALLOWED
                Text(turnMsg)
            }
        }
        if (message != null) {
            Text(text = message, color = Color.Red)
        }
        if (model.jobAutoRefresh != null) CircularProgressIndicator()
    }

}

