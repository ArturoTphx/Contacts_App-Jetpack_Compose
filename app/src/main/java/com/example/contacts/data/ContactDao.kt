package com.example.contacts.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts ORDER BY name ASC")
    fun getContacts(): Flow<List<Contact>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(contact: Contact)

    @Query("DELETE FROM contacts WHERE ide = :deleteId")
    fun delete(deleteId: Int)

    @Query("UPDATE contacts SET name = :nameU, lastnameOne = :lastNameOneU, lastNameTwo = :lastNameTwoU, number = :numberU WHERE ide = :idU")
    fun update(idU: Int, nameU: String, lastNameOneU: String, lastNameTwoU: String, numberU: String)

}