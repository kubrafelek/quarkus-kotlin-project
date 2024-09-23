package org.example.config

import io.quarkus.elytron.security.common.BcryptUtil
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class PasswordHelper {

    fun encodePwd(input: String): String {
        return BcryptUtil.bcryptHash(input)
    }

    fun isPasswordValid(input: String, hashPassword: String): Boolean {
        return BcryptUtil.matches(input, hashPassword)
    }
}