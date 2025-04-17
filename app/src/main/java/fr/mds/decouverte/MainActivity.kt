package fr.mds.decouverte


import NotesScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.mds.decouverte.data.db.AppDatabase
import fr.mds.decouverte.data.repositories.NoteRepository
import fr.mds.decouverte.data.viewModels.NoteViewModel
import fr.mds.decouverte.data.viewModels.factories.NoteViewModelFactory
import fr.mds.decouverte.presentation.notes.NoteDetailScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appDatabase = AppDatabase.getDatabase(application)
        val noteRepository = NoteRepository(noteDao = appDatabase.noteDao())
        val noteViewModel = NoteViewModelFactory(noteRepository)

        setContent {
            MyApp(noteViewModel)
        }
    }
}

@Composable
fun MyApp(noteViewModelFactory: NoteViewModelFactory) {
    val navController = rememberNavController()
    val viewModel: NoteViewModel = viewModel(factory = noteViewModelFactory)

    NavHost(navController, startDestination = "notesList") {
        composable("notesList") {
            NotesScreen(viewModel) { noteId ->
                navController.navigate("noteDetail/$noteId")
            }
        }
        composable("noteDetail/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toLongOrNull()
            if (noteId != null) {
                NoteDetailScreen(viewModel, navController, noteId)
            }
        }
    }
}