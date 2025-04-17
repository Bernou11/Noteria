import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.mds.decouverte.data.entities.Note
import fr.mds.decouverte.data.viewModels.NoteViewModel
import fr.mds.decouverte.presentation.notes.UpdateNoteTitleDialog

@Composable
fun NotesScreen(viewModel: NoteViewModel, onNoteClick: (Long) -> Unit) {
    val notesState by viewModel.notes.collectAsState()

    val titleState = remember { mutableStateOf("") }
    val contentState = remember { mutableStateOf("") }
    var noteToUpdate by remember { mutableStateOf<Note?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadNotes()
    }

    Column {
        TextField(value = titleState.value, onValueChange = { titleState.value = it }, label = { Text("Title") })
        TextField(value = contentState.value, onValueChange = { contentState.value = it }, label = { Text("Content") })

        Button(onClick = {
            val newTitle = titleState.value.ifEmpty { "No title" }

            val newNote = Note(title = newTitle, content = contentState.value)

            viewModel.addNote(newNote)

            titleState.value = "" // Clear input
            contentState.value = "" // Clear input
        }) {
            Text("Add Note")
        }

        LazyColumn {
            items(notesState) { note ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = note.title.ifBlank { "No title" },
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNoteClick(note.id) }
                    )
                    IconButton(
                        onClick = { viewModel.delete(note) },
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Delete note")
                    }
                    IconButton(
                        onClick = {
                            noteToUpdate = note // Set the note to update
                            showDialog = true // Show the dialog
                        },
                        modifier = Modifier.padding(start = 8.dp) // Add spacing
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit note")
                    }
                }
            }
        }
        if (showDialog && noteToUpdate != null) {
            UpdateNoteTitleDialog(
                note = noteToUpdate!!,
                onDismiss = { showDialog = false; noteToUpdate = null },
                onUpdate = { updatedNote ->
                    viewModel.updateNoteTitle(updatedNote) // Perform the update
                    showDialog = false // Close dialog after update
                }
            )
        }
    }
}