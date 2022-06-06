package com.androiddev.smartnotes.feature_note.presentation.notes

import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.toLowerCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.domain.repository.NoteRepository
import com.androiddev.smartnotes.feature_note.presentation.common.BaseViewModel
import com.androiddev.smartnotes.feature_note.presentation.util.BaseEvent
import com.androiddev.smartnotes.feature_note.presentation.util.SortBy
import com.androiddev.smartnotes.feature_note.presentation.util.SortOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val repository: NoteRepository
): BaseViewModel() {
    var state by mutableStateOf(NotesState())
    private set

    private var getNotesJob: Job? = null
    private lateinit var unfilteredNotes: List<Note>

    init {
        getNotes()
    }

    fun onEvent(event: BaseEvent) {
        when(event) {
            is NotesEvent.BackPressed -> {
                if(state.selectionMode) {
                    state = state.copy(
                        selectionMode = false,
                        selectedNotes = emptySet()
                    )
                }

            }

            is NotesEvent.Search -> {
                state = state.copy(
                    searchText = event.value
                )
                searchNotes(event.value)
            }
            is NotesEvent.SortNotes -> {
                viewModelScope.launch {
                    sortNotes(event.sortBy, event.sortOrder)
                }
            }

            is NotesEvent.ToggleSelectionMode -> {
                state = state.copy(
                    selectionMode = !state.selectionMode
                )
            }
            is NotesEvent.ToggleSelectNote -> {
                val selectedNotes = state.selectedNotes.toMutableSet()
                if(selectedNotes.contains(event.note)) {
                    selectedNotes.remove(event.note)
                } else {
                    selectedNotes.add(event.note)
                }
                state = state.copy(
                    selectedNotes = selectedNotes,
                    selectionMode = if(selectedNotes.isEmpty()) false else state.selectionMode
                )

            }
            is NotesEvent.DeleteNotes -> {
                viewModelScope.launch {
                    for(note in state.selectedNotes) {
                        repository.deleteNote(note)
                    }
                    state = state.copy(
                        selectedNotes = emptySet(),
                        selectionMode = false
                    )
                    Log.d("NotesViewModel", "SUCCESS: Selected notes deleted")
                }
            }
        }
    }

    private fun sortNotes(sortBy: SortBy, sortOrder: SortOrder) {

        val sortedNotes: List<Note> = when(sortOrder) {
            SortOrder.DESCENDING -> {
                when(sortBy) {
                    SortBy.DATE_MODIFIED -> {
                        state.notes.sortedWith(compareBy {it.dateModified})
                    }
                    SortBy.DATE_CREATED -> {
                        state.notes.sortedWith(compareBy {it.dateCreated})
                    }
                    SortBy.NOTE_TITLE -> {
                        state.notes.sortedWith(compareBy {it.title.lowercase()})
                    }
                    SortBy.NOTE_COLOR -> {
                        state.notes.sortedWith(compareBy {it.color})
                    }
                }
            }
            SortOrder.ASCENDING -> {
                when(sortBy) {
                    SortBy.DATE_MODIFIED -> {
                        state.notes.sortedWith(compareBy {it.dateModified}).reversed()
                    }
                    SortBy.DATE_CREATED -> {
                        state.notes.sortedWith(compareBy {it.dateCreated}).reversed()
                    }
                    SortBy.NOTE_TITLE -> {
                        state.notes.sortedWith(compareBy {it.title.lowercase()}).reversed()
                    }
                    SortBy.NOTE_COLOR -> {
                        state.notes.sortedWith(compareBy {it.color}).reversed()
                    }
                }
            }
        }
        state = state.copy(
            notes = sortedNotes,
        )
    }

    private fun getNotes() {
        getNotesJob?.cancel()
        getNotesJob = repository.getAllNotes()
            .onEach { notes ->
                unfilteredNotes = notes
                state = state.copy(
                    notes = notes,
                )
                Log.d("NotesViewModel", "notes count_: ${state.notes.size}")
            }
            .launchIn(viewModelScope)
    }

    fun getAnnotatedString(
        text: String,
        searchKey: String,
        highlightStyle: SpanStyle
    ): AnnotatedString {
        val builder = AnnotatedString.Builder(text)
        if(searchKey.isBlank())
            return builder.toAnnotatedString()
        val startIndex = text.indexOf(searchKey, 0, true)
        if(startIndex < 0) {
            return builder.toAnnotatedString()
        }
        val endIndex = startIndex + searchKey.length
        builder.addStyle(highlightStyle, startIndex, endIndex)
        return builder.toAnnotatedString()
    }

    private fun searchNotes(searchKey: String) {
        if(searchKey.isBlank())
            return getNotes()
        val filteredNotes: List<Note> = unfilteredNotes.filter { note ->
            note.title.indexOf(searchKey, 0, true) > 0 ||
                note.content.indexOf(searchKey, 0, true) > 0
        }
        state = state.copy(
            notes = filteredNotes
        )
    }


    private fun sanitizeSearchKey(searchKey: String): String {
        if(searchKey.isBlank())
            return ""
        val searchKey = searchKey.replace(Regex.fromLiteral("\""), "\"\"")
        Log.d("SearchNotes", "*$searchKey*")
        return "*$searchKey*"
    }
}