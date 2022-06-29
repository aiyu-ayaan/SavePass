package com.ab.core.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPassword(passwordModel: PasswordModel)

    @Update
    suspend fun updatePassword(passwordModel: PasswordModel)

    @Delete
    suspend fun deletePassword(passwordModel: PasswordModel)

    @Query("SELECT * FROM password_table")
    fun getAllPasswords(): Flow<List<PasswordModel>>

    @Query("SELECT * FROM password_table WHERE username like :userName")
    fun getPassword(userName: String): Flow<List<PasswordModel>>

    @Query("Delete from password_table")
    suspend fun deleteAll()


}