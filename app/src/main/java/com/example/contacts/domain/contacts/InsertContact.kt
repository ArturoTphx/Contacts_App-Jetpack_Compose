package com.example.contacts.domain.contacts

import com.example.contacts.data.contacts.ContactsRepository
import com.example.contacts.domain.contacts.model.Contact
import com.example.contacts.domain.contacts.model.toContactEntity
import javax.inject.Inject

class InsertContact @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(contact: Contact) {
        contactsRepository.insert(contact.toContactEntity())
    }
}