package com.example.contacts.domain.contacts.model

import com.example.contacts.data.contacts.local.ContactEntity

data class Contact(
    val id: Int = 0,
    val name: String = "",
    val lastNameOne: String = "",
    val lastNameTwo: String = "",
    val number: String = ""
) {
    fun fullName() = "$name $lastNameOne $lastNameTwo"
}

fun ContactEntity.toContact() = Contact(
    ide, name, lastNameOne, lastNameTwo, number
)

fun Contact.toContactEntity() = ContactEntity(
    id, name, lastNameOne, lastNameTwo, number
)