package com.androiddev.smartnotes.feature_note.domain.repository

import com.androiddev.smartnotes.feature_note.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {

    fun getAllNotes(): Flow<List<Note>>

    suspend fun insertNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(noteId: Int): Note?

    fun searchNotes(searchKey: String): Flow<List<Note>>

    suspend fun rebuildFTSIndex()
}