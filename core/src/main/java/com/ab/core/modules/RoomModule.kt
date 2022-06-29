package com.ab.core.modules

import android.content.Context
import androidx.room.Room
import com.ab.core.room.PasswordDao
import com.ab.core.room.PasswordDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun providePasswordDatabase(
        @ApplicationContext context: Context
    ): PasswordDatabase = Room.databaseBuilder(
        context,
        PasswordDatabase::class.java,
        PasswordDatabase.DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .build()

    @Singleton
    @Provides
    fun providePasswordDao(
        database: PasswordDatabase
    ): PasswordDao = database.passwordDao()
}