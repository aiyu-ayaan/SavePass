package com.ab.core.repositories

import com.scottyab.aescrypt.AESCrypt
import java.security.GeneralSecurityException
import javax.inject.Inject

class AESCryptRepository @Inject constructor() {

    fun encrypt(userName: String, password: String): String? {
        return try {
            AESCrypt.encrypt(userName, password)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            null
        }
    }

    fun decrypt(userName: String, encryptedPassword: String): String? {
        return try {
            AESCrypt.decrypt(userName, encryptedPassword)
        } catch (e: GeneralSecurityException) {
            e.printStackTrace()
            null
        }
    }
}