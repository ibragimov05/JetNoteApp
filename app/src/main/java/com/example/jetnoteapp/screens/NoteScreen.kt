package com.example.jetnoteapp.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
    onEditClick: (Note) -> Unit,
    onDeleteAll: () -> Unit,
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
                    IconButton(
                        onClick = { onDeleteAll() }
                    ) {
                        Icon(imageVector = Icons.Rounded.Done, contentDescription = "Done")
                    }
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
                        onEditClick = {
                            onEditClick(note)
                        }
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
    onEditClick: (Note) -> Unit,
) {
    val openAlertDialog = remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(16.dp)),
        color = Color(0xFFDFE6EB),
        tonalElevation = 6.dp,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 14.dp, vertical = 6.dp,
            ),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            onNoteClicked(note)
                        },
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

            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .clickable(
                        onClick = {
                            openAlertDialog.value = true
                        },
                        indication = rememberRipple(),
                        interactionSource = remember { MutableInteractionSource() }
                    ),
                tonalElevation = 10.dp,
                shape = RoundedCornerShape(10.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Edit")
                }
            }
        }
    }

    if (openAlertDialog.value) {
        EditAlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            onConfirmation = {
                openAlertDialog.value = false
                onEditClick(note)
            },
            dialogTitle = "Edit Note",
            icon = Icons.Default.Info,
            note = note,
        )
    }
}


@Composable
fun EditAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (Note) -> Unit,
    dialogTitle: String,
    note: Note,
    icon: ImageVector,
) {
    val newTitle = remember { mutableStateOf("") }
    val newDescription = remember { mutableStateOf("") }

    newTitle.value = note.title
    newDescription.value = note.description

    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column {
                NoteInputTex(
                    text = newTitle.value,
                    label = "2345",
                    onTextChange = { char ->
                        if (char.all { it.isLetter() || it.isWhitespace() })
                            newTitle.value = char
                    },
                )
                NoteInputTex(
                    text = newDescription.value,
                    label = "2345",
                    onTextChange = { char ->
                        if (char.all { it.isLetter() || it.isWhitespace() })
                            newDescription.value = char
                    },
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation(
                        Note(
                            id = note.id,
                            title = newTitle.value,
                            description = newDescription.value,
                        )
                    )
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}
