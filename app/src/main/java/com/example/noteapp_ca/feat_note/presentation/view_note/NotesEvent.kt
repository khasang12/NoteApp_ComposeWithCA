package com.example.noteapp_ca.feat_note.presentation.view_note

import com.example.noteapp_ca.feat_note.domain.model.Note
import com.example.noteapp_ca.feat_note.domain.util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    data object RestoreNote: NotesEvent()
    data object ToggleOrderSection: NotesEvent()
}