package com.androiddev.smartnotes.feature_note.data.data_source

import androidx.room.*
import com.androiddev.smartnotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getAllNotes(): Flow<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM note WHERE note.id = :noteId")
    suspend fun getNoteById(noteId: Int): Note?

    @Query("""
        SELECT note.id,
            CASE substr(offsets(note_fts), 1, 1)
                WHEN '1' THEN snippet(note_fts)
                ELSE note.title
            END title,
            CASE substr(offsets(note_fts), 1, 1)
                WHEN '2' THEN snippet(note_fts)
                ELSE note.content
            END content, 
            note.color,
            note.dateModified,
            note.isFavourite,
            note.dateCreated,
            note.isHidden,
            note.lastPosition
        FROM note
        JOIN note_fts ON note.id == note_fts.id
        WHERE note_fts MATCH :searchKey
    """)
    fun search(searchKey: String): Flow<List<Note>>

    @Query("INSERT INTO note_fts(note_fts) VALUES ('rebuild')")
    suspend fun rebuildFTSIndex()
}