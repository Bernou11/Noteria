package fr.mds.decouverte.presentation.notes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import fr.mds.decouverte.data.entities.Note

@Composable
fun UpdateNoteContentDialog(note: Note, onDismiss: () -> Unit, onUpdate: (Note) -> Unit) {
    var newContent by remember { mutableStateOf(note.content) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Note") },
        text = {
            TextField(
                value = newContent,
                onValueChange = { newContent = it },
                label = { Text("Content") }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedNote = note.copy(content = newContent)
                onUpdate(updatedNote)

            }) {
                Text("Update")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}