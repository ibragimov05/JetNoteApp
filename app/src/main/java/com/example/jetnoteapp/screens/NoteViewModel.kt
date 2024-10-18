package com.example.jetnoteapp.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.jetnoteapp.data.NotesDataSource
import com.example.jetnoteapp.models.Note

class NoteViewModel : ViewModel() {
    private val noteList = mutableStateListOf<Note>()

    init {
        noteList.addAll(NotesDataSource().loadNotes())
    }

    fun getAllNotes(): List<Note> = noteList


    fun addNote(note: Note) = noteList.add(note)

    fun removeNote(note: Note) = noteList.remove(note)

}