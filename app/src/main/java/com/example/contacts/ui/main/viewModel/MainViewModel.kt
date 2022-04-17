package com.example.contacts.ui.main.viewModel

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.R
import com.example.contacts.domain.contacts.DeleteContact
import com.example.contacts.domain.contacts.GetContacts
import com.example.contacts.domain.contacts.InsertContact
import com.example.contacts.domain.contacts.UpdateContact
import com.example.contacts.domain.contacts.model.Contact
import com.example.contacts.ui.main.interaction.MainState
import com.example.contacts.ui.main.interaction.HomeEvent
import com.example.contacts.utilities.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getContacts: GetContacts,
    private val insertContact: InsertContact,
    private val deleteContact: DeleteContact,
    private val updateContact: UpdateContact
) : ViewModel() {

    private val _state: MutableState<MainState> = mutableStateOf(MainState())
    val state: State<MainState> get() = _state

    init {
        collectContacts()
    }

    private fun collectContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            val one = async { getContacts() }
            withContext(Dispatchers.Main) {
                _state.value = _state.value.copy(contacts = one.await())
            }
        }
    }

    fun onEvent(homeEvent: HomeEvent) {
        when(homeEvent) {
            is HomeEvent.AddContact -> {
                add(
                    name = homeEvent.name,
                    lastNameOne = homeEvent.lastNameOne,
                    lastNameTwo = homeEvent.lastNameTwo,
                    number = homeEvent.number
                )
            }
            is HomeEvent.DeleteContact -> {
                delete(homeEvent.id)
            }
            is HomeEvent.UpdateContact -> {
                update(
                    id = homeEvent.id,
                    name = homeEvent.name,
                    lastNameOne = homeEvent.lastNameOne,
                    lastNameTwo = homeEvent.lastNameTwo,
                    number = homeEvent.number
                )
            }
            is HomeEvent.ToAdd -> {
                adding()
            }
            is HomeEvent.ToConsult -> {
                _state.value = _state.value.copy(selected = Contact())
                consulting()
            }
            is HomeEvent.ToUpdate -> {
                _state.value = _state.value.copy(selected = homeEvent.contact)
                updating()
            }
            is HomeEvent.ToHideError -> {
                hideError()
            }
            is HomeEvent.ToHideInfo -> {
                hideInfo()
            }
        }
    }

    private fun add(name: String, lastNameOne: String, lastNameTwo: String, number: String) {
        val error: Int? = if (name.isEmpty() || lastNameOne.isEmpty() || lastNameTwo.isEmpty()) {
            R.string.empty_fields
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(name)
                .matches()
        ) {
            R.string.name_error
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE)
                .matcher(lastNameOne).matches()
            || !Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(lastNameTwo)
                .matches()
        ) {
            R.string.last_name_error
        } else if (!Patterns.PHONE.matcher(number).matches()) {
            R.string.phone_error
        } else null

        error?.let {
            _state.value = _state.value.copy(error = it)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            insertContact(Contact(0, name, lastNameOne, lastNameTwo, number))
            _state.value = _state.value.copy(info = R.string.success)
            collectContacts()
            consulting()
        }
    }

    private fun delete(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteContact(id)
            _state.value = _state.value.copy(info = R.string.success)
            collectContacts()
            consulting()
        }
    }

    private fun update(id: Int, name: String, lastNameOne: String, lastNameTwo: String, number: String) {
        val error: Int? = if (name.isEmpty() || lastNameOne.isEmpty() || lastNameTwo.isEmpty()) {
            R.string.empty_fields
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(name)
                .matches()
        ) {
            R.string.name_error
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE)
                .matcher(lastNameOne).matches()
            || !Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(lastNameTwo)
                .matches()
        ) {
            R.string.last_name_error
        } else if (!Patterns.PHONE.matcher(number).matches()) {
            R.string.phone_error
        } else null

        error?.let {
            _state.value = _state.value.copy(error = it)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            updateContact(Contact(id, name, lastNameOne, lastNameTwo, number))
            _state.value = _state.value.copy(info = R.string.success)
            collectContacts()
            consulting()
        }
    }

    private fun consulting() {
        _state.value = _state.value.copy(screen = Constants.consulting, selected = Contact())
    }

    private fun adding() {
        _state.value = _state.value.copy(screen = Constants.adding, selected = Contact())
    }

    private fun updating() {
        _state.value = _state.value.copy(screen = Constants.updating)
    }

    private fun hideError() {
        _state.value = _state.value.copy(error = null)
    }

    private fun hideInfo() {
        _state.value = _state.value.copy(info = null)
    }
}