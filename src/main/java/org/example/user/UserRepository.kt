package org.example.user

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import io.quarkus.panache.common.Parameters.with
import jakarta.enterprise.context.ApplicationScoped

@ApplicationScoped
class UserRepository : PanacheRepositoryBase<User, String> {
    fun findByEmail(email: String): User? =
        find("upper(email)", email.uppercase().trim()).firstResult()

    fun existsUsername(subjectedUserId: String): Boolean = count(
        query = "id.username = :subjectedUserId",
        params = with("subjectedUserId", subjectedUserId)
    ) > 0

    fun existsEmail(subjectedUserEmail: String): Boolean = count(
        query = "upper(email) = :subjectedUserEmail",
        params = with("subjectedUserEmail", subjectedUserEmail.uppercase().trim())
    ) > 0
}