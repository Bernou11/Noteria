package fr.mds.decouverte.presentation.notes

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import fr.mds.decouverte.data.entities.Note

@Composable
fun UpdateNoteTitleDialog(note: Note, onDismiss: () -> Unit, onUpdate: (Note) -> Unit) {
    var newTitle by remember { mutableStateOf(note.title) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Update Note") },
        text = {
            TextField(
                value = newTitle,
                onValueChange = { newTitle = it },
                label = { Text("Title") }
            )
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedNote = note.copy(title = newTitle) // Assuming `Note` is a data class
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