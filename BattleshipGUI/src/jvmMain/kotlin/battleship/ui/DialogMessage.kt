package battleship.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState

@Composable
@Preview
fun testDialog() = ContentMessage("Xpto", {})

@Composable
fun DialogMessage(message: String, onOk: () -> Unit) = Dialog(
    onCloseRequest = onOk,
    title = "Message",
    state = DialogState(height = Dp.Unspecified, width = 350.dp)
) {
    ContentMessage(message, onOk)
}

@Composable
private fun ContentMessage(message: String, onOk: () -> Unit) = Column(
    Modifier.fillMaxWidth().background(Color.Yellow),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(message)
    Button(onClick = onOk) { Text("Ok") }
}