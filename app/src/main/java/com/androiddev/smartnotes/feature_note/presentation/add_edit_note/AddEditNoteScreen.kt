package com.androiddev.smartnotes.feature_note.presentation.add_edit_note

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.twotone.Circle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.androiddev.smartnotes.feature_note.presentation.common.components.BottomBar
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.BottomBarButton
import com.androiddev.smartnotes.feature_note.presentation.add_edit_note.components.TextFieldWithHint
import com.androiddev.smartnotes.feature_note.presentation.common.BaseViewModel
import com.androiddev.smartnotes.feature_note.presentation.util.Screen
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
) {
    val state = viewModel.state
    val noteTitle = viewModel.noteTitle
    val noteContent = viewModel.noteContent

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            BottomBar(
                items = listOf(
                    BottomBarButton(
                        icon = Icons.Default.Home,
                        name = "Home",
                        onClick = {
                            scope.launch {
                                navController.navigate(Screen.NotesScreen.route)
                            }
                        },
                    ),
                    BottomBarButton(
                        icon = Icons.Default.Circle,
                        name = "Color",
                        color = state.value.color,
                        modifier = Modifier.size(30.dp),
                        onClick = {
                            scope.launch {
                                viewModel.onEvent(AddEditNoteEvent.ToggleIsColorSelectorVisible)
                            }
                        }
                    ),
                    BottomBarButton(
                        icon = Icons.Default.Share,
                        name = "Share",
                        onClick = {

                        }
                    ),
                    BottomBarButton(
                        icon = Icons.Default.Delete,
                        name = "Delete",
                        onClick = {
                            scope.launch {
                                viewModel.onEvent(AddEditNoteEvent.DeleteNote(state.value.noteId))
                                navController.navigateUp()
                            }
                        }
                    ),
                ),
                onItemClick = {
                    it.onClick()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        viewModel.onEvent(AddEditNoteEvent.SaveNote)
                        navController.navigateUp()
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = if (isSystemInDarkTheme())
                            Color(state.value.color).copy(alpha = 0.2f)
                        else Color(state.value.color))
                .padding(16.dp)

        ) {
            TextFieldWithHint(
                text = noteTitle.value.text,
                hintText = "Title",
                isSingleLine = true,
                isFocused = noteTitle.value.isFocused,
//                textStyle = MaterialTheme.typography.h5,
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 24.sp,
                ),
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleFocusChange(it))
                },
                onTextChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
            Divider(
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 0.2.dp
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithHint(
                text = noteContent.value.text,
                hintText = "Content...",
                isSingleLine = false,

                isFocused = noteContent.value.isFocused,
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentFocusChange(it))
                },
                onTextChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentChange(it))
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp,
                ),
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}