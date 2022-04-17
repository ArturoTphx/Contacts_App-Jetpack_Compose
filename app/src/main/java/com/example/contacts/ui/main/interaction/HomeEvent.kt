package com.example.contacts.ui.main.interaction

import com.example.contacts.domain.contacts.model.Contact

sealed class HomeEvent {
    data class AddContact(val name: String,val lastNameOne: String,val lastNameTwo: String,val number: String): HomeEvent()
    data class DeleteContact(val id:Int): HomeEvent()
    data class UpdateContact(val id: Int, val name: String,val lastNameOne: String,val lastNameTwo: String,val number: String): HomeEvent()
    object ToConsult: HomeEvent()
    object ToAdd: HomeEvent()
    data class ToUpdate(val contact: Contact): HomeEvent()
    object ToHideInfo: HomeEvent()
    object ToHideError: HomeEvent()
}
