package org.example.repository

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import org.example.model.Account
import org.example.model.Role

@ApplicationScoped
class AuthRepository : PanacheRepositoryBase<Account, Long> {

    fun getUserByUsername(username: String): Account? {
        if (username.isBlank()) {
            return null
        }
        return find("username", username).firstResult<Account>()
    }

    fun getRolesByUsername(username: String): List<Role> {
        return find("username", username).firstResult<Account>()?.authorities?.toList() ?: emptyList()
    }
}