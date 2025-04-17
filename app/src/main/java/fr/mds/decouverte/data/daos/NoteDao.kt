package fr.mds.decouverte.data.daos

import androidx.room.*
import fr.mds.decouverte.data.entities.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Note>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteById(id: Long): Note?

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)
}