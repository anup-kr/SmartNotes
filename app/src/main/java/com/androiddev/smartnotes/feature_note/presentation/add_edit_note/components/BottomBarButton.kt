package com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteEvent

data class BottomBarButton(
    val name: String,
    val icon: ImageVector,
    val color: Int = -1,
    val isEnabled: Boolean = true,
    val modifier: Modifier = Modifier,
    val onClick: () -> Unit
)
