package fr.mds.decouverte.data.repositories

import fr.mds.decouverte.data.daos.NoteDao
import fr.mds.decouverte.data.entities.Note

class NoteRepository(private val noteDao: NoteDao) {

    suspend fun getAllNotes(): List<Note> = noteDao.getAllNotes()

    suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)
    }

    suspend fun insert(note: Note) = noteDao.insert(note)

    suspend fun delete(note: Note) = noteDao.delete(note)

    suspend fun update(note: Note) = noteDao.update(note)
}