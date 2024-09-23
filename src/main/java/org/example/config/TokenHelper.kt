package org.example.config

import io.smallrye.jwt.build.Jwt
import jakarta.inject.Singleton
import org.example.model.Account
import org.example.service.AuthService

@Singleton
class TokenHelper(private val usersSvc: AuthService) {

//    @ConfigProperty(name = "mp.jwt.verify.issuer")
    private var issuer: String = "Issuer"

    private fun setClaims(user: Account): Map<String, Any> {
        val roles = usersSvc.getUserGroups(user.username)
        return mapOf("email" to user.username, "groups" to roles)
    }

    fun getToken(user: Account): String {
        return generateToken(user)
    }

    private fun generateToken(user: Account): String {
        return Jwt.claims(setClaims(user)).issuer(issuer).sign()
    }
}