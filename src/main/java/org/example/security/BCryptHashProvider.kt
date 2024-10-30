package org.example.security

import at.favre.lib.crypto.bcrypt.BCrypt.verifyer
import at.favre.lib.crypto.bcrypt.BCrypt.withDefaults
import jakarta.enterprise.context.ApplicationScoped
import org.eclipse.microprofile.config.inject.ConfigProperty

@ApplicationScoped
class BCryptHashProvider(
    @ConfigProperty(name = "bcrypt.hash.cost")
    private val hashCost: Int,
) {
    fun hash(password: String): String =
        withDefaults().hashToString(hashCost, password.toCharArray())

    fun verify(plaintext: String, hash: String): Boolean =
        verifyer().verify(plaintext.toCharArray(), hash).verified
}