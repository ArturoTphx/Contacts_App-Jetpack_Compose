package com.example.contacts.data.contacts

import com.example.contacts.data.contacts.local.ContactDao
import com.example.contacts.data.contacts.local.ContactEntity
import javax.inject.Inject

class ContactsRepository @Inject constructor(private val contactDao: ContactDao){

    suspend fun get(): List<ContactEntity> {
        return contactDao.getContacts()
    }

    suspend fun delete(id: Int) {
        contactDao.delete(id)
    }

    suspend fun insert(contactEntity: ContactEntity) {
        contactDao.insert(contactEntity)
    }

    suspend fun update(contactEntity: ContactEntity) {
        contactDao.update(contactEntity.ide, contactEntity.name, contactEntity.lastNameOne, contactEntity.lastNameTwo, contactEntity.number)
    }


}