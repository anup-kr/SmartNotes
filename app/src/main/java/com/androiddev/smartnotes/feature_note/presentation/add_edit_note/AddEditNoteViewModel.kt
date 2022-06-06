package com.androiddev.smartnotes.feature_note.presentation.add_edit_note

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.androiddev.smartnotes.feature_note.domain.model.InvalidNoteException
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.domain.repository.NoteRepository
import com.androiddev.smartnotes.feature_note.presentation.common.BaseViewModel
import com.androiddev.smartnotes.feature_note.presentation.common.TextFieldState
import com.androiddev.smartnotes.feature_note.presentation.util.BaseEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val repository: NoteRepository,
    savedStateHandle: SavedStateHandle
): BaseViewModel() {

    var noteTitle = mutableStateOf(TextFieldState())
    private var _noteTitle: MutableState<TextFieldState> = noteTitle

    var noteContent = mutableStateOf(TextFieldState())
    private var _noteContent: MutableState<TextFieldState> = noteContent

    var state = mutableStateOf(AddEditNoteState())
    private var _state: MutableState<AddEditNoteState> = state

    private var currentNoteId: Int? = null
    private var currentNote: Note? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if(noteId != -1) {
                viewModelScope.launch {
                    val note: Note? = repository.getNoteById(noteId)
                    note?.let { note ->
                        currentNote = note
                        currentNoteId = note.id
                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isFocused = false,
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isFocused = false,
                        )
                        _state.value = state.value.copy(
                            noteId = note.id ?: -1,
                            isFavourite = note.isFavourite,
                            isHidden = note.isHidden,
                            color = note.color,
                        )
                    }
                }
            }
            else {
                _state.value = state.value.copy(
                    color = Note.noteColors.random().toArgb()
                )
                Log.d("AddEditNoteViewModel", "init the random color .........")
            }
        }
    }

    fun onEvent(event: BaseEvent) {
        when(event) {
            is AddEditNoteEvent.TitleChange -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.TitleFocusChange -> {
                _noteTitle.value = noteTitle.value.copy(
                    isFocused = event.focusState.isFocused,
//                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isNullOrBlank()
                )
            }
            is AddEditNoteEvent.ContentChange -> {
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ContentFocusChange -> {
                _noteContent.value = noteContent.value.copy(
                    isFocused = event.focusState.isFocused,
//                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isNullOrBlank()
                )
            }
            is AddEditNoteEvent.ChangeColor -> {
                _state.value = state.value.copy(
                    color = event.color
                )
            }
            is AddEditNoteEvent.ToggleIsColorSelectorVisible -> {
                _state.value = state.value.copy(
                    isColorSelectorExpanded = !state.value.isColorSelectorExpanded
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        repository.insertNote(
                            Note(
                                id = currentNoteId,
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                color = state.value.color,
                                dateCreated = System.currentTimeMillis(),
                                dateModified = System.currentTimeMillis(),
                                isHidden = false,
                                isFavourite = false,
                                lastPosition = "0,0"
                            )
                        )
                    } catch (e: InvalidNoteException) {
                        Log.e("SaveNote", e.message ?: "Something went wrong!!!!!")
                    }
                }
            }

            is AddEditNoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    try {
                        currentNote?.let {
                            repository.deleteNote(currentNote!!)
                        }
                    } catch(e: Exception) {
                        Log.e("DeleteNote", e.message ?: "Something went wrong!!!!!")
                    }
                }
            }
        }
    }
}