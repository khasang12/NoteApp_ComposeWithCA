package com.example.noteapp_ca.feat_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp_ca.ui.theme.BabyBlue
import com.example.noteapp_ca.ui.theme.LightGreen
import com.example.noteapp_ca.ui.theme.RedOrange
import com.example.noteapp_ca.ui.theme.RedPink
import com.example.noteapp_ca.ui.theme.Violet

@Entity
data class Note(
    @PrimaryKey val id: Int? = null,
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int
){
    companion object {
        val colors = listOf(RedOrange, LightGreen, Violet, BabyBlue, RedPink)
    }
}
