package fr.mds.decouverte.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.mds.decouverte.data.entities.Note
import fr.mds.decouverte.data.repositories.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> get() = _notes

    private val _selectedNote = MutableStateFlow<Note?>(null)
    val selectedNote: StateFlow<Note?> get() = _selectedNote

    fun loadNotes() {
        viewModelScope.launch {
            _notes.value = repository.getAllNotes()
        }
    }

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insert(note)
            loadNotes()
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch {
            _selectedNote.value = repository.getNoteById(id)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch {
            repository.delete(note)
            loadNotes()
        }
    }

    fun updateNoteTitle(updatedNote: Note) {
        viewModelScope.launch {
            repository.update(updatedNote)
            loadNotes()
        }
    }

    fun updateNoteContent(updatedNote: Note) {
        viewModelScope.launch {
            repository.update(updatedNote)
            getNoteById(updatedNote.id)
        }
    }
}