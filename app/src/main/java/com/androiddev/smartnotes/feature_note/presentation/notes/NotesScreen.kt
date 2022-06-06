package com.androiddev.smartnotes.feature_note.presentation.notes

import SortSection
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.BottomBarButton
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.TextFieldWithHint
import com.androiddev.smartnotes.feature_note.presentation.common.components.BottomBar
import com.androiddev.smartnotes.feature_note.presentation.common.components.BottomBar
import com.androiddev.smartnotes.feature_note.presentation.notes.components.NoteItem
import com.androiddev.smartnotes.feature_note.presentation.notes.components.SearchField
import com.androiddev.smartnotes.feature_note.presentation.util.Screen
import com.androiddev.smartnotes.feature_note.presentation.util.SortBy
import com.androiddev.smartnotes.feature_note.presentation.util.SortOrder
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotesScreen(
    navController: NavController,
    viewModel: NotesViewModel = hiltViewModel(),
    ) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Log.d("NotesScreen", "count at top: ${viewModel.state.notes.size}")

    val notesState = viewModel.state

    BackHandler(enabled = true, onBack = {
        Log.d("BackHandler", "Back pressed.......")
        viewModel.onEvent(NotesEvent.BackPressed)
    })

    Scaffold(
        floatingActionButton = {
            if(!notesState.selectionMode) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddEditNoteScreen.route)
                    },
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
                }
            }
        },
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomBarButton(
                        icon = Icons.Default.Delete,
                        name = "Delete",
                        onClick = {
                            scope.launch {
                                viewModel.onEvent(
                                    NotesEvent.DeleteNotes(notesState.selectedNotes)
                                )
                            }
                        }
                    ),
                ),
                isBottomBarVisible = notesState.selectionMode,
                onItemClick = {
                    it.onClick()
                }
            )
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Smart Notes",
                    modifier = Modifier,
                    color = MaterialTheme.colors.onBackground,
                    style = TextStyle(
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 32.sp,
                    ),
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "more",
                    modifier = Modifier.clickable {

                    }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SearchField(
                    onSearchTextChange = {
                        viewModel.onEvent(NotesEvent.Search(it))
                    }
                )
                SortSection()
            }

            if(notesState.selectionMode) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 4.dp, vertical = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {

                            }
                    ) {
                        Icon(imageVector = Icons.Outlined.Circle, contentDescription = "select all")
                        Text(text = "All")
                    }
                    Text(
                        text = notesState.selectedNotes.size.toString() + " Selected",
//                        modifier = Modifier.padding(4.dp)
                    )
                }
                
            } else {
                Spacer(modifier = Modifier.height(16.dp))
            }
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
            )
             {
                 Log.d("NotesScreen", "Notes count: ${notesState.notes.size}")
                items(notesState.notes) { note ->
                    Log.d("NotesScreen", "in LazyVerticalGrid....")
                    NoteItem(
                        note = note,
                        viewModel = viewModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                interactionSource = MutableInteractionSource(),
                                indication = null,
                                onClick = {
                                    if (!notesState.selectionMode) {
                                        navController.navigate(
                                            Screen.AddEditNoteScreen.route + "?noteId=${note.id}"
                                        )
                                    } else {
                                        Log.d("NotesScree", "combined onClick clicked...")
                                        scope.launch {
                                            viewModel.onEvent(NotesEvent.ToggleSelectNote(note))
                                        }
                                    }
                                },
                                onLongClick = {
                                    if (!notesState.selectionMode) {
                                        scope.launch {
                                            viewModel.onEvent(NotesEvent.ToggleSelectionMode)
                                            viewModel.onEvent(NotesEvent.ToggleSelectNote(note))
                                        }
                                    }
                                }
                            )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

}