package com.androiddev.smartnotes.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.presentation.util.BaseEvent

sealed class AddEditNoteEvent: BaseEvent {
    data class TitleChange(val value: String): BaseEvent
    data class ContentChange(val value: String): BaseEvent
    data class TitleFocusChange(val focusState: FocusState): BaseEvent
    data class ContentFocusChange(val focusState: FocusState): BaseEvent
    object SaveNote: BaseEvent
    data class ChangeColor(val color: Int): BaseEvent
    object ToggleIsColorSelectorVisible: BaseEvent
    data class LikeNote(val note: Note): BaseEvent
    data class DeleteNote(val noteId: Int): BaseEvent
    data class HideNote(val note: Note): BaseEvent
    object RestoreClicked: BaseEvent
}
