package com.example.contacts.main

import com.example.contacts.data.Contact

data class MainState(
    val contacts: List<Contact> = emptyList(),
    val contact: Contact = Contact(0,"","","", ""),
    val isConsulting: Boolean = true,
    val isAdding: Boolean = false,
    val isUpdating: Boolean = false,
    val error: Int? = null,
    val info: Int? = null
)
