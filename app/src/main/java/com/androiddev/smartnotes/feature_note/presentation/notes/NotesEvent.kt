package com.androiddev.smartnotes.feature_note.presentation.notes

import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.presentation.util.BaseEvent
import com.androiddev.smartnotes.feature_note.presentation.util.SortBy
import com.androiddev.smartnotes.feature_note.presentation.util.SortOrder

sealed class NotesEvent: BaseEvent {
    data class Search(val value: String): BaseEvent
    data class SortNotes(
        val sortBy: SortBy,
        val sortOrder: SortOrder
    ): BaseEvent
    object SortDropDownChange: BaseEvent
    object ToggleSelectionMode: BaseEvent
    data class ToggleSelectNote(val note: Note): BaseEvent
    data class DeleteNotes(val notes: Set<Note>): BaseEvent
    object BackPressed: BaseEvent
}
