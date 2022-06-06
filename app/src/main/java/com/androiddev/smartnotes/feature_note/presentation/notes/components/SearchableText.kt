package com.androiddev.smartnotes.feature_note.presentation.notes.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androiddev.smartnotes.feature_note.presentation.common.TextFieldState
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesViewModel

@Composable
fun SearchableText(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Text(
        viewModel.getAnnotatedString(
            text = text,
            state.searchText,
            SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)
        ),
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        overflow = TextOverflow.Ellipsis,
        modifier = modifier
    )
}