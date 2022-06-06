package com.androiddev.smartnotes.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androiddev.smartnotes.feature_note.domain.model.Note
import com.androiddev.smartnotes.feature_note.domain.model.NoteFTS

@Database(
    entities = [Note::class, NoteFTS::class],
    version = 2
)
abstract class NoteDatabase: RoomDatabase(){
    abstract val noteDao: NoteDao
}