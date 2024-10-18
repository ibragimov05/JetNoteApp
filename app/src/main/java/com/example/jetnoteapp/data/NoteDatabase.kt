package com.example.jetnoteapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.jetnoteapp.models.Note
import com.example.jetnoteapp.util.DateConvertor
import com.example.jetnoteapp.util.UUIDConvertor

@Database(entities = [Note::class], version = 1, exportSchema = false)
@TypeConverters(DateConvertor::class, UUIDConvertor::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDatabaseDao
}