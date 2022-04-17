package com.example.contacts.data.contacts.local

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactsRoomDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}