package tds.galo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogState

const val DIALOG_DEFAULT_ENTRY = "Game Name"

@Composable
fun DialogName(onCancel: () -> Unit, onStart: (String) -> Unit) = Dialog(
    onCloseRequest = onCancel,
    title = DIALOG_DEFAULT_ENTRY,
    state = DialogState(height = Dp.Unspecified, width = 350.dp)
) {
    var name by remember { mutableStateOf(DIALOG_DEFAULT_ENTRY) }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            name,
            onValueChange = { name = it },
            label = { Text("name") }
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (name.isNotBlank()) onStart(name)
            }) { Text("Start") }
            Button(onClick = onCancel) { Text("Cancel") }
        }
    }
}