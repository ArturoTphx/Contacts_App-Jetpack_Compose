package com.example.contacts.ui.main.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.contacts.R
import com.example.contacts.ui.components.MyOptionDialog
import com.example.contacts.ui.components.MyOutlinedTextField
import com.example.contacts.ui.main.interaction.HomeEvent
import com.example.contacts.ui.main.viewModel.MainViewModel

@Composable
fun Updating(
    viewModel: MainViewModel
) {
    val contactSelected = viewModel.state.value.selected
    val name = rememberSaveable { mutableStateOf(contactSelected.name) }
    val lastNameOne = rememberSaveable { mutableStateOf(contactSelected.lastNameOne) }
    val lastNameTwo = rememberSaveable { mutableStateOf(contactSelected.lastNameTwo) }
    val number = rememberSaveable { mutableStateOf(contactSelected.number) }
    val focusManager = LocalFocusManager.current
    var eraseDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(HomeEvent.ToConsult)
                }
            ) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Fab button")
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        backgroundColor = MaterialTheme.colors.background
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 15.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MyOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                fieldValue = name,
                label = R.string.name_hint,
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                imeAction = ImeAction.Next
            )

            MyOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                fieldValue = lastNameOne,
                label = R.string.last_name_one_hint,
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                imeAction = ImeAction.Next
            )

            MyOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                fieldValue = lastNameTwo,
                label = R.string.last_name_two_hint,
                keyboardType = KeyboardType.Text,
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                imeAction = ImeAction.Next
            )

            MyOutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                fieldValue = number,
                label = R.string.phone_hint,
                keyboardType = KeyboardType.Number,
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                imeAction = ImeAction.Done
            )

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { viewModel.onEvent(
                    HomeEvent.UpdateContact(
                        id = contactSelected.id,
                        name = name.value,
                        lastNameOne = lastNameOne.value,
                        lastNameTwo = lastNameTwo.value,
                        number = number.value,
                    )
                )
                }
            ) {
                Text(text = stringResource(id = R.string.save), style = MaterialTheme.typography.button)
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { eraseDialog = true }
            ) {
                Text(text = stringResource(id = R.string.delete), style = MaterialTheme.typography.button)
            }

        }
        if (eraseDialog) {
            MyOptionDialog(
                message = R.string.question,
                acceptLabel = R.string.yes,
                dismissLabel = R.string.no,
                onDismiss = { eraseDialog = false },
                onAccept = {viewModel.onEvent(HomeEvent.DeleteContact(contactSelected.id));eraseDialog = false}
            )
        }

    }
}