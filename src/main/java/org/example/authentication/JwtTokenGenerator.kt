package org.example.authentication

import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JwtTokenGenerator {
    fun generateToken(username: String, roles: List<String>): String {
        return Jwt.claims()
            .issuer("https://your-issuer")
            .subject(username)
            .groups(HashSet(roles)) // Use roles as groups
            .sign()
    }
}