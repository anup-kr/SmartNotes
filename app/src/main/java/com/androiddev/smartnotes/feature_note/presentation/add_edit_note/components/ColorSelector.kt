package com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteState
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.androiddev.smartnotes.feature_note.presentation.common.BaseViewModel
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesEvent
import kotlinx.coroutines.launch

@Composable
fun ColorSelector(
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val state = viewModel.state.value

    DropdownMenu(
        expanded = state.isColorSelectorExpanded,
        onDismissRequest = {
            scope.launch {
                viewModel.onEvent(AddEditNoteEvent.ToggleIsColorSelectorVisible)
            }
        },
        modifier = Modifier
            .width(64.dp)
            .clip(RoundedCornerShape(40))
    ) {
        for(color in Note.noteColors) {
            DropdownMenuItem(
                onClick = {
                    scope.launch {
                        viewModel.onEvent(AddEditNoteEvent.ChangeColor(color.toArgb()))
                        viewModel.onEvent(AddEditNoteEvent.ToggleIsColorSelectorVisible)
                    }
                },
            ) {
//                Text(text = "Color", color=color)
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = color.toString(),
                    tint = color,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    }

}