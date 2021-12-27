package com.example.contacts.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val ide: Int,
    val name: String,
    val lastNameOne: String,
    val lastNameTwo: String,
    val number: String
) {
    fun fullName() = "$name $lastNameOne $lastNameTwo"
}
