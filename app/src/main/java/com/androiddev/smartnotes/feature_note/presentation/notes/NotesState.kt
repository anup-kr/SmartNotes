package com.androiddev.smartnotes.feature_note.presentation.notes

import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.presentation.util.SortBy
import com.androiddev.smartnotes.feature_note.presentation.util.SortOrder
import kotlinx.coroutines.flow.Flow

data class NotesState(
    val notes: List<Note> = emptyList(),
    val searchText: String = "",
    val selectionMode: Boolean = false,
    val selectedNotes: Set<Note> = mutableSetOf<Note>()

)