package com.androiddev.smartnotes.feature_note.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import com.androiddev.smartnotes.ui.theme.*
import java.sql.Timestamp

@Entity
class Note(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val content: String,
    val dateCreated: Long,
    val dateModified: Long,
    val color: Int,
    val isFavourite: Boolean,
    val isHidden: Boolean,
    val lastPosition: String
) {
    companion object {
        val noteColors = listOf(GoldGreen, PurePurple, RareRed, YellYellow, BabyBlue, RedOrange)
    }
}

@Entity(tableName = "note_fts")
@Fts4(contentEntity = Note::class)
data class NoteFTS(
    val id: Int? = null,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String
)

class InvalidNoteException(message: String): Exception(message)