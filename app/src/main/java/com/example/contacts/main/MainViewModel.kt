package com.example.contacts.main

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contacts.R
import com.example.contacts.data.ContactsRoomDatabase
import com.example.contacts.data.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.regex.Pattern

class MainViewModel: ViewModel() {

    private lateinit var contactsRoomDatabase: ContactsRoomDatabase

    private val _state: MutableState<MainState> = mutableStateOf(MainState())
    val state: State<MainState> get() = _state

    @InternalCoroutinesApi
    fun initialize(context: Context) {
        contactsRoomDatabase = ContactsRoomDatabase.getInstance(context)
        viewModelScope.launch(Dispatchers.IO) {
            contactsRoomDatabase.contactDao().getContacts().collect {  list->
                withContext(Dispatchers.Main) { _state.value = _state.value.copy(contacts = list,isConsulting = true)}
            }
        }
    }

    private fun reload() {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRoomDatabase.contactDao().getContacts().collect {  list->
                withContext(Dispatchers.Main) { _state.value = _state.value.copy(contacts = list); _state.value = _state.value.copy(isConsulting = true, isAdding = false, isUpdating = false)}
            }
        }
    }

    fun consulting() {_state.value = _state.value.copy(isConsulting = true, isUpdating = false, isAdding = false)}

    fun adding() {_state.value = _state.value.copy(isConsulting = false, isUpdating = false, isAdding = true)}

    fun updating() {_state.value = _state.value.copy(isConsulting = false, isUpdating = true, isAdding = false) }

    fun insert(name: String, lastNameOne: String, lastNameTwo: String, number: String) {
        val error: Int? = if (name.isEmpty()||lastNameOne.isEmpty()||lastNameTwo.isEmpty()) {
            R.string.empty_fields
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(name).matches()) {
            R.string.name_error
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(lastNameOne).matches()
            || !Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(lastNameTwo).matches()) {
            R.string.last_name_error
        } else if (!Patterns.PHONE.matcher(number).matches()) {
            R.string.phone_error
        } else null

        error?.let {
            _state.value = _state.value.copy(error = it)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            contactsRoomDatabase.contactDao().insert(Contact(0, name, lastNameOne, lastNameTwo, number))
            _state.value = _state.value.copy( isConsulting = false, isUpdating = true, isAdding = true, info = R.string.success)
        }

        reload()
    }

    fun update(id: Int, name: String, lastNameOne: String, lastNameTwo: String, number: String) {
        val error: Int? = if (name.isEmpty()||lastNameOne.isEmpty()||lastNameTwo.isEmpty()) {
            R.string.empty_fields
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(name).matches()) {
            R.string.name_error
        } else if (!Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(lastNameOne).matches()
            || !Pattern.compile("\\A\\w{4,20}\\z", Pattern.CASE_INSENSITIVE).matcher(lastNameTwo).matches()) {
            R.string.last_name_error
        } else if (!Patterns.PHONE.matcher(number).matches()) {
            R.string.phone_error
        } else null

        error?.let {
            _state.value = _state.value.copy(error = it)
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            contactsRoomDatabase.contactDao().update(id, name, lastNameOne, lastNameTwo, number)
            _state.value = _state.value.copy( info = R.string.success)
        }

        reload()
    }

    fun erase(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            contactsRoomDatabase.contactDao().delete(id)
            _state.value = _state.value.copy( info = R.string.success)
        }

        reload()
    }

    fun hideError() {
        _state.value = _state.value.copy(error = null)
    }

    fun hideInfo() {
        _state.value = _state.value.copy(info = null)
    }
}