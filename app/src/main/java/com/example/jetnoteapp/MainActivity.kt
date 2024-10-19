package com.example.jetnoteapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetnoteapp.screens.NoteScreen
import com.example.jetnoteapp.screens.NoteViewModel
import com.example.jetnoteapp.ui.theme.JetNoteAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetNoteAppTheme {
                /* Options 1 */
                // val noteViewModel: NoteViewModel by viewModels<NoteViewModel>();

                /* Options 2 */
                val noteViewModel = viewModel<NoteViewModel>()
                NotesApp(noteViewModel = noteViewModel)
            }
        }
    }
}


@Composable
fun NotesApp(noteViewModel: NoteViewModel) {
    val notesList = noteViewModel.noteList.collectAsState().value

    NoteScreen(
        notes = notesList,
        onAddNote = { noteViewModel.addNote(it) },
        onRemoveNote = { noteViewModel.removeNote(it) },
        onEditClick = { noteViewModel.updateNote(it) },
        onDeleteAll = { noteViewModel.deleteAll()}
    )
}