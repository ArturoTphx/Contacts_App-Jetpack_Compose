package com.example.contacts.di

import android.content.Context
import androidx.room.Room
import com.example.contacts.data.contacts.local.ContactsRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideContactsDatabase(@ApplicationContext app: Context) =
        Room.databaseBuilder(app, ContactsRoomDatabase::class.java, "contacts_database").build()

    @Provides
    @Singleton
    fun provideContactsDao(db: ContactsRoomDatabase) = db.contactDao()
}