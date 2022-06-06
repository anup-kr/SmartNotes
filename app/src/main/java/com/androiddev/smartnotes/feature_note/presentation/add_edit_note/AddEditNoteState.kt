package com.androiddev.smartnotes.feature_note.presentation.add_edit_note

import androidx.compose.ui.graphics.toArgb
import com.androiddev.smartnotes.feature_note.domain.model.Note
import kotlin.random.Random

data class AddEditNoteState (
    val color: Int = -1,
    val noteId: Int = -1,
    val isFavourite: Boolean = false,
    val isHidden: Boolean = false,
    val isColorSelectorExpanded: Boolean = false
)

