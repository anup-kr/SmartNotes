package com.androiddev.smartnotes.feature_note.util

import android.app.Application
import androidx.room.Room
import com.androiddev.smartnotes.feature_note.data.data_source.NoteDatabase
import com.androiddev.smartnotes.feature_note.data.repository.NoteRepositoryImpl
import com.androiddev.smartnotes.feature_note.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDBModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            "notes_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository {
        return NoteRepositoryImpl(db.noteDao)
    }

}