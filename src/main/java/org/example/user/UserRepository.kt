package org.example.user

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepository<User> {

    fun findById(id: String): User? =
        find("id", id).firstResult()

    fun findByUsername(username: String): User =
        find("username", username).stream().findFirst().get()

    fun findByEmail(email: String): User? =
        find("email", email).firstResult()

    fun existsByUsername(username: String): Boolean =
        count("username", username) > 0

    fun existsByEmail(email: String): Boolean =
        count("email", email) > 0
}