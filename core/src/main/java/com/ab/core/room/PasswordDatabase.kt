package com.ab.core.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [PasswordModel::class], version = 1,exportSchema = false)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao

    companion object {
        const val DATABASE_NAME = "password_database"
    }
}