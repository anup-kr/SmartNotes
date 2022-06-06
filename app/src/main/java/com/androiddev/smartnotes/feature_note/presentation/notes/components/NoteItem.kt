package com.androiddev.smartnotes.feature_note.presentation.notes.components

import android.util.Log
import android.widget.GridLayout
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesState
import com.androiddev.smartnotes.feature_note.presentation.notes.NotesViewModel
import org.intellij.lang.annotations.JdkConstants
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun NoteItem(
    note: Note,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    Box(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
//            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Card(
                elevation = 16.dp,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(start = 8.dp, top = 12.dp, end = 8.dp, bottom = 4.dp)
                    .height(240.dp)
                    .fillMaxWidth(),
                backgroundColor = if(isSystemInDarkTheme())
                        Color(note.color).copy(alpha = 0.2f)
                    else Color(note.color),
//                border = if(state.selectedNotes.contains(note)) BorderStroke(4.dp, Color.Green) else null

            ) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)) {
                    SearchableText(
                        text = note.content,
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 5.dp, end = 10.dp, bottom = 5.dp)
                    )
                }
            }
            SearchableText(
                text = note.title,
                color = MaterialTheme.colors.onBackground,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
            Text(
//                text = SimpleDateFormat("dd/M/yyyy hh:mm").format(note.dateModified),
                text = "May 10",
                color = MaterialTheme.colors.onBackground,
                fontSize = 13.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if(state.selectionMode && state.selectedNotes.contains(note)) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "selected icon",
                tint = Color(0xFF2C8048)
            )
        }
        else if(state.selectionMode && !state.selectedNotes.contains(note)) {
            Icon(
                imageVector = Icons.Outlined.Circle,
                contentDescription = "selected icon",
                tint = Color(0xFFC48B60)
            )
        }
    }

}