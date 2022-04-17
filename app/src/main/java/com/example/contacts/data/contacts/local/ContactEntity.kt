package com.example.contacts.data.contacts.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey(autoGenerate = true) val ide: Int,
    val name: String,
    val lastNameOne: String,
    val lastNameTwo: String,
    val number: String
)
