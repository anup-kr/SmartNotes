package com.androiddev.smartnotes.feature_note.presentation.notes.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.TextFieldWithHint
import com.androiddev.smartnotes.feature_note.presentation.common.TextFieldState
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesViewModel


@Composable
fun SearchField(
    onSearchTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel()
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    var searchFieldState by remember { mutableStateOf(TextFieldState()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(
                color = if (searchFieldState.isFocused)
                    MaterialTheme.colors.onSurface.copy(alpha = 0.1f)
                else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp)
    ) {
        TextFieldWithHint(
            text = viewModel.state.searchText,
            hintText = "Search...",
            isSingleLine = true,
            isFocused = searchFieldState.isFocused,
            textStyle = TextStyle(
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
            ),
            onFocusChange = {
                searchFieldState = searchFieldState.copy(
                    isFocused = it.isFocused
                )
            },
            onTextChange = {
                searchFieldState = searchFieldState.copy(
                    text = it
                )
                onSearchTextChange(it)
            },
            modifier = modifier
                .width(180.dp)
                .focusRequester(focusRequester),
        )
        RemoveTextIcon(
            isVisible = searchFieldState.text.isNotBlank() || searchFieldState.isFocused,
            onClick = {
                searchFieldState = searchFieldState.copy(
                    text = ""
                )
                onSearchTextChange("")
                focusManager.clearFocus()
            }
        )
    }
}

@Composable
fun RemoveTextIcon(
    isVisible: Boolean,
    onClick: () -> Unit
) {
    if(isVisible) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = "delete search text",
            modifier = Modifier
                .size(16.dp)
                .clickable {
                    onClick()
                }
        )
    }
}