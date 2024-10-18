package com.example.jetnoteapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetnoteapp.R
import com.example.jetnoteapp.components.NoteButton
import com.example.jetnoteapp.components.NoteInputTex
import com.example.jetnoteapp.models.Note
import com.example.jetnoteapp.util.formatDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit,
) {
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
                actions = {
                    Icon(
                        imageVector = Icons.Rounded.Done, contentDescription = "Done",
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Hello World")
            NoteInputTex(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 9.dp, bottom = 9.dp,
                    ),
                text = title.value,
                label = "Title",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) title.value = it
                },
            )

            NoteInputTex(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 9.dp, bottom = 9.dp,
                    ),
                text = description.value,
                label = "Add a note",
                onTextChange = {
                    if (it.all { char ->
                            char.isLetter() || char.isWhitespace()
                        }) description.value = it
                },
            )



            NoteButton(
                text = "Save",
                onClick = {
                    if (title.value.isNotEmpty() && description.value.isNotEmpty()) {
                        onAddNote(
                            Note(
                                title = title.value,
                                description = description.value,
                            ),
                        )

                        title.value = ""
                        description.value = ""

                        Toast.makeText(context, "Note Added", Toast.LENGTH_SHORT).show()
                    }
                },
            )

            HorizontalDivider(modifier = Modifier.padding(10.dp))

            LazyColumn {
                items(notes) { note ->
                    NoteRow(
                        modifier = Modifier.fillMaxWidth(),
                        note = note,
                        onNoteClicked = {
                            onRemoveNote(note)
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit,
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp)),
        color = Color(0xFFDFE6EB),
        tonalElevation = 6.dp,
    ) {
        Column(
            modifier = Modifier
                .clickable(
                    onClick = {
                        onNoteClicked(note)
                    },
                )
                .padding(
                    horizontal = 14.dp, vertical = 6.dp,
                ),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Text(text = note.description, style = MaterialTheme.typography.titleSmall)
            Text(
                text = formatDate(note.entryDate.time),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}