package com.example.contacts.ui.main.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.contacts.R
import com.example.contacts.domain.contacts.model.Contact
import com.example.contacts.ui.main.interaction.HomeEvent
import com.example.contacts.ui.main.viewModel.MainViewModel

@Composable
fun Consulting(
    viewModel: MainViewModel,
    onUpdate: (Int, String, String, String, String) -> Unit
) {
    val contacts = viewModel.state.value.contacts
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(HomeEvent.ToAdd) }
            ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Fab button")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) {
        if (contacts.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.empty),
                    style = MaterialTheme.typography.subtitle1
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                itemsIndexed(
                    items = viewModel.state.value.contacts
                ) { _, contact ->
                    ContactCard(
                        contact = contact,
                        onClick = onUpdate
                    )
                }
            }
        }
    }
}

@Composable
fun ContactCard(
    contact: Contact,
    onClick: (Int, String, String, String, String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(contact.id, contact.name, contact.lastNameOne, contact.lastNameTwo, contact.number)
            },
        elevation = 1.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = contact.fullName(),
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = contact.number,
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}