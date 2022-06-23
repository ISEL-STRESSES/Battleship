package battleship.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState

val DIALOG_COLOR = Color.Red

@Composable
fun DialogWarning(message: String, onOk: () -> Unit) = Dialog(
    onCloseRequest = onOk,
    title = "Warning",
    state = DialogState(height = Dp.Unspecified, width = DIALOG_WIDTH.dp)
) {
    ContentMessage(message, onOk)
}

@Composable
private fun ContentMessage(message: String, onOk: () -> Unit) = Column(
    Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(text = message, fontWeight = FontWeight.Bold, color = DIALOG_COLOR)
    Button(onClick = onOk) { Text("Ok") }
}