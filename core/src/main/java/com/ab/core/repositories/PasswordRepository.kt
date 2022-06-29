package com.ab.core.repositories

import com.ab.core.room.PasswordDao
import com.ab.core.room.PasswordModel
import javax.inject.Inject


class PasswordRepository @Inject constructor(
    private val dao: PasswordDao
) {
    fun getPassword(id: String) = dao.getPassword(id)
    fun getAllPasswords() = dao.getAllPasswords()
    suspend fun insertPassword(password: PasswordModel) = dao.addPassword(password)
    suspend fun updatePassword(password: PasswordModel) = dao.updatePassword(password)
    suspend fun deletePassword(password: PasswordModel) = dao.deletePassword(password)
}