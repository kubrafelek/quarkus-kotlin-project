package org.example.authentication

import io.smallrye.jwt.build.Jwt
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class JwtTokenGenerator {
    fun generateToken(username: String, roles: List<String>, userId: String): String {
        return Jwt.claims()
            .issuer("https://your-issuer")       // Set the issuer claim
            .subject(username)                   // Set the subject (username)
            .groups(HashSet(roles))              // Set the roles as groups
            .claim("userId", userId)             // Add custom claim (userId)
            .sign()                              // Sign the token using the private key
    }
}