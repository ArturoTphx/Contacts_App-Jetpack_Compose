package com.example.contacts.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.contacts.R
import com.example.contacts.data.Contact
import com.example.contacts.presentation.components.*
import com.example.contacts.ui.theme.ContactsTheme
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = viewModel()
            viewModel.initialize(this)
            ContactsTheme {
                MainScreen()
            }
        }
    }

    @Composable
    fun MainScreen() {

        val id = rememberSaveable { mutableStateOf(0) }
        val name = rememberSaveable { mutableStateOf("") }
        val lastNameOne = rememberSaveable { mutableStateOf("") }
        val lastNameTwo = rememberSaveable { mutableStateOf("") }
        val number = rememberSaveable { mutableStateOf("")}
        val focusManager = LocalFocusManager.current
        var question by remember { mutableStateOf(false) }

        Box(modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxSize()
        ){
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface)
            ) {
                if (viewModel.state.value.isConsulting) {
                    Scaffold(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxSize(),
                        floatingActionButton = {
                            TransitionButton(
                                onTransition = { viewModel.adding() })
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) {
                        if (viewModel.state.value.contacts.isEmpty()) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colors.surface),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = stringResource(id = R.string.empty),
                                    style = MaterialTheme.typography.h5
                                )
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize()
                                    .background(MaterialTheme.colors.surface)
                            ) {
                                itemsIndexed(
                                    items = viewModel.state.value.contacts
                                ) { _, contact ->
                                    ContactCard(
                                        contact = contact,
                                        id = id,
                                        name = name,
                                        lastNameOne = lastNameOne,
                                        lastNameTwo = lastNameTwo,
                                        number = number,
                                        onClick = viewModel::updating
                                    )
                                }
                            }
                        }
                    }
                }
                if (viewModel.state.value.isUpdating) {
                    Scaffold(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxSize(),
                        floatingActionButton = {
                            UpdateButton(
                                imageVector = Icons.Filled.Done,
                                id = id,
                                name = name,
                                lastNameOne = lastNameOne,
                                lastNameTwo = lastNameTwo,
                                number = number,
                                onUpdate = viewModel::update
                            )
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.surface)
                                .padding(horizontal = 15.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomTextField(
                                textFieldValue = name,
                                textLabel = stringResource(R.string.name_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            CustomTextField(
                                textFieldValue = lastNameOne,
                                textLabel = stringResource(R.string.last_name_one_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            CustomTextField(
                                textFieldValue = lastNameTwo,
                                textLabel = stringResource(R.string.last_name_two_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            CustomTextField(
                                textFieldValue = number,
                                textLabel = stringResource(R.string.phone_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                                imeAction = ImeAction.Done
                            )

                            Spacer(modifier = Modifier.height(5.dp))

                            CustomButton(
                                text = stringResource(R.string.delete),
                                onClick = {
                                    question = !question
                                }
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            ClickableText(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colors.onSurface,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    ) {
                                        append(stringResource(R.string.back))
                                    }
                                },
                                onClick = { viewModel.consulting() }
                            )

                        }
                    }
                }
                if (viewModel.state.value.isAdding) {
                    Scaffold(
                        modifier = Modifier
                            .background(MaterialTheme.colors.surface)
                            .fillMaxSize(),
                        floatingActionButton = {
                            AddButton(
                                imageVector = Icons.Filled.Done,
                                name = name,
                                lastNameOne = lastNameOne,
                                lastNameTwo = lastNameTwo,
                                number = number,
                                onInsert = viewModel::insert
                            )
                        },
                        floatingActionButtonPosition = FabPosition.End
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.surface)
                                .padding(horizontal = 15.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CustomTextField(
                                textFieldValue = name,
                                textLabel = stringResource(R.string.name_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            CustomTextField(
                                textFieldValue = lastNameOne,
                                textLabel = stringResource(R.string.last_name_one_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            CustomTextField(
                                textFieldValue = lastNameTwo,
                                textLabel = stringResource(R.string.last_name_two_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                imeAction = ImeAction.Next
                            )

                            CustomTextField(
                                textFieldValue = number,
                                textLabel = stringResource(R.string.phone_hint),
                                keyboardType = KeyboardType.Text,
                                keyboardActions = KeyboardActions(
                                    onDone = { focusManager.clearFocus() }
                                ),
                                imeAction = ImeAction.Done
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            ClickableText(
                                text = buildAnnotatedString {
                                    withStyle(
                                        style = SpanStyle(
                                            color = MaterialTheme.colors.onSurface,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 16.sp
                                        )
                                    ) {
                                        append(stringResource(R.string.back))
                                    }
                                },
                                onClick = { viewModel.consulting() }
                            )

                        }

                    }
                }

            }
            if (question) {
                YesNoDialog(message = R.string.question, onDismiss = { question = !question }, onAccept = { viewModel.erase(id.value); question = !question; name.value = ""; lastNameOne.value = ""; lastNameTwo.value = ""; number.value = ""})
            }
            if (viewModel.state.value.error != null) {
                viewModel.state.value.error?.let { error ->
                    EventDialog(errorMessage = error, onDismiss = { viewModel.hideError() })
                }
            }
            if (viewModel.state.value.info != null) {
                viewModel.state.value.info?.let { info ->
                    InfoDialog(message = info, onDismiss = {viewModel.hideInfo() })
                }
            }
        }
    }

    @Composable
    fun ContactCard(
        contact: Contact,
        id: MutableState<Int>,
        name: MutableState<String>,
        lastNameOne: MutableState<String>,
        lastNameTwo: MutableState<String>,
        number: MutableState<String>,
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .background(MaterialTheme.colors.surface)
                .height(80.dp)
                .clickable {
                    id.value = contact.ide
                    name.value = contact.name
                    lastNameOne.value = contact.lastNameOne
                    lastNameTwo.value = contact.lastNameTwo
                    number.value = contact.number
                    onClick()
                },
            border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
            elevation = 8.dp
        ) {
            Column(modifier = Modifier
                .fillMaxSize().padding(horizontal = 10.dp, vertical = 10.dp), verticalArrangement = Arrangement.spacedBy(5.dp), horizontalAlignment = Alignment.Start) {
                Text(text = contact.fullName(), style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface), overflow = TextOverflow.Ellipsis, maxLines = 1)
                Text(text = contact.number, style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.onSurface, fontSize = 17.sp, fontStyle = FontStyle.Normal), overflow = TextOverflow.Ellipsis, maxLines = 1)
            }
        }
    }

    @Composable
    fun TransitionButton(
        imageVector: ImageVector = Icons.Filled.Add,
        backgroundColor: Color = MaterialTheme.colors.background,
        contentColor: Color = MaterialTheme.colors.onSurface,
        onTransition: () -> Unit
    ) {
        FloatingActionButton(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            onClick =  { onTransition() }
        ) {
            Icon(imageVector = imageVector, contentDescription = "Fab button")
        }
    }


    @Composable
    fun AddButton(
        imageVector: ImageVector = Icons.Filled.Add,
        backgroundColor: Color = MaterialTheme.colors.background,
        contentColor: Color = MaterialTheme.colors.onSurface,
        name: MutableState<String>,
        lastNameOne: MutableState<String>,
        lastNameTwo: MutableState<String>,
        number: MutableState<String>,
        onInsert: (String, String, String, String) -> Unit
    ) {
        name.value = ""
        lastNameOne.value = ""
        lastNameTwo.value = ""
        number.value = ""
        FloatingActionButton(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            onClick =  { onInsert(name.value, lastNameOne.value, lastNameTwo.value, number.value) }
        ) {
            Icon(imageVector = imageVector, contentDescription = "Fab button")
        }
    }

    @Composable
    fun UpdateButton(
        imageVector: ImageVector = Icons.Filled.Add,
        backgroundColor: Color = MaterialTheme.colors.background,
        contentColor: Color = MaterialTheme.colors.onSurface,
        id: MutableState<Int>,
        name: MutableState<String>,
        lastNameOne: MutableState<String>,
        lastNameTwo: MutableState<String>,
        number: MutableState<String>,
        onUpdate: (Int, String, String, String, String) -> Unit
    ) {
        FloatingActionButton(
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            onClick =  { onUpdate(id.value, name.value, lastNameOne.value, lastNameTwo.value, number.value) }
        ) {
            Icon(imageVector = imageVector, contentDescription = "Fab button")
        }
    }

}