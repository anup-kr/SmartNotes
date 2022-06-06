package com.androiddev.smartnotes.feature_note.data.repository

import com.androiddev.smartnotes.feature_note.data.data_source.NoteDao
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val noteDao: NoteDao
): NoteRepository {

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override suspend fun insertNote(note: Note) {
        noteDao.insertNote(note)
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    override suspend fun getNoteById(noteId: Int): Note? {
        return noteDao.getNoteById(noteId)
    }

    override fun searchNotes(searchKey: String): Flow<List<Note>> {
        return noteDao.search(searchKey)
    }

    override suspend fun rebuildFTSIndex() {
        noteDao.rebuildFTSIndex()
    }
}