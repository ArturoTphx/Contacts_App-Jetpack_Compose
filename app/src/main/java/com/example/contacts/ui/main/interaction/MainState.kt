package com.example.contacts.ui.main.interaction

import com.example.contacts.domain.contacts.model.Contact
import com.example.contacts.utilities.Constants

data class MainState(
    val contacts: List<Contact> = emptyList(),
    val selected: Contact = Contact(),
    val screen: String = Constants.consulting,
    val error: Int? = null,
    val info: Int? = null
)
