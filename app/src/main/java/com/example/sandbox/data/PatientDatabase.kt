package com.example.sandbox.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        // common entities
        Entry::class,
    ],
)
abstract class SandboxDatabase : RoomDatabase() {

    companion object {
        const val NAME = "local_db"

        val INSTANCE by lazy {
            Room.databaseBuilder(SandboxApplication.INSTANCE, SandboxDatabase::class.java, NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun entryDAO(): EntryDAO
}