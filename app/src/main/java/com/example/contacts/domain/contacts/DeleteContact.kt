package com.example.contacts.domain.contacts

import com.example.contacts.data.contacts.ContactsRepository
import javax.inject.Inject

class DeleteContact @Inject constructor(private val contactsRepository: ContactsRepository) {
    suspend operator fun invoke(id: Int) {
        contactsRepository.delete(id)
    }
}