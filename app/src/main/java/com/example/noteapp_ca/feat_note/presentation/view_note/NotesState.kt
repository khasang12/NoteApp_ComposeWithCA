package com.example.noteapp_ca.feat_note.presentation.view_note

import com.example.noteapp_ca.feat_note.domain.model.Note
import com.example.noteapp_ca.feat_note.domain.util.NoteOrder
import com.example.noteapp_ca.feat_note.domain.util.OrderType

data class NotesState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)