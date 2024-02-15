package com.example.noteapp_ca.feat_note.presentation.view_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp_ca.feat_note.domain.model.Note
import com.example.noteapp_ca.feat_note.domain.use_case.NoteUseCases
import com.example.noteapp_ca.feat_note.domain.util.NoteOrder
import com.example.noteapp_ca.feat_note.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases
) : ViewModel() {
    private val _state = mutableStateOf<NotesState>(NotesState())
    val state: State<NotesState> = _state

    private var recentlyDeletedNote: Note? = null
    private var getNotesJob: Job? = null

    init {
        // Get all notes in default mode
        noteUseCases.getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent) {
        when (event) {
            is NotesEvent.Order -> {
                // same state -> do nothing
                if (state.value.noteOrder::class == event.noteOrder::class &&
                    state.value.noteOrder.orderType::class == event.noteOrder.orderType::class
                ) {
                    return
                }
                getNotesJob?.cancel()// cancel any previous jobs
                getNotesJob = noteUseCases.getNotes(event.noteOrder)
                    .onEach { notes ->
                        _state.value = state.value.copy(
                            notes = notes,
                            noteOrder = event.noteOrder
                        )
                    }
                    .launchIn(viewModelScope)
            }

            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote(event.note)
                    recentlyDeletedNote = event.note
                }
            }

            is NotesEvent.RestoreNote -> {
                viewModelScope.launch {
                    noteUseCases.addNote(recentlyDeletedNote ?: return@launch)
                    recentlyDeletedNote = null
                }
            }

            is NotesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }
}