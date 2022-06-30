package com.ab.core.repositories

import at.favre.lib.crypto.bcrypt.BCrypt
import javax.inject.Inject

class BcryptRepository @Inject constructor() {
    fun hash(password: String): String =
        BCrypt.withDefaults().hashToString(12, password.toCharArray())

    fun check(password: String, hash: String): Boolean =
        BCrypt.verifyer().verify(password.toCharArray(), hash).verified
}
