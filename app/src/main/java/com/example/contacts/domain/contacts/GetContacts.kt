package com.example.contacts.domain.contacts

import com.example.contacts.data.contacts.ContactsRepository
import com.example.contacts.domain.contacts.model.Contact
import com.example.contacts.domain.contacts.model.toContact
import javax.inject.Inject

class GetContacts @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(): List<Contact> {
        return contactsRepository.get().map { it.toContact() }
    }
}