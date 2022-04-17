package com.example.contacts.data.contacts.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    suspend fun getContacts(): List<ContactEntity>

    @Insert(onConflict = REPLACE)
    suspend fun insert(contact: ContactEntity)

    @Query("DELETE FROM contacts WHERE ide = :deleteId")
    suspend fun delete(deleteId: Int)

    @Query("UPDATE contacts SET name = :nameU, lastnameOne = :lastNameOneU, lastNameTwo = :lastNameTwoU, number = :numberU WHERE ide = :idU")
    suspend fun update(idU: Int, nameU: String, lastNameOneU: String, lastNameTwoU: String, numberU: String)

}