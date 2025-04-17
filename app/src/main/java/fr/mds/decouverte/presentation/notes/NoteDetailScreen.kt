package fr.mds.decouverte.presentation.notes

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.mds.decouverte.data.entities.Note
import fr.mds.decouverte.data.viewModels.NoteViewModel

@Composable
fun NoteDetailScreen(viewModel: NoteViewModel, navController: NavController, noteId: Long) {
    val noteState by viewModel.selectedNote.collectAsState(initial = null)
    var noteToUpdate by remember { mutableStateOf<Note?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(noteId) {
        viewModel.getNoteById(noteId)
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        noteState?.let { note ->
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
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
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium
            )
        } ?: Text(text = "Note not found")
    }

    if (showDialog && noteToUpdate != null) {
        UpdateNoteContentDialog(
            note = noteToUpdate!!,
            onDismiss = { showDialog = false; noteToUpdate = null },
            onUpdate = { updatedNote ->
                viewModel.updateNoteContent(updatedNote)
                showDialog = false
            }
        )
    }
}