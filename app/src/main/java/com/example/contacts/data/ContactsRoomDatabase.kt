package com.example.contacts.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

const val nameOfDB = "contacts_database"

@Database(entities = [Contact::class], version = 1)
abstract class ContactsRoomDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE: ContactsRoomDatabase? = null

        @InternalCoroutinesApi
        fun getInstance(
            context: Context
        ) : ContactsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance: ContactsRoomDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsRoomDatabase::class.java,
                    nameOfDB
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}