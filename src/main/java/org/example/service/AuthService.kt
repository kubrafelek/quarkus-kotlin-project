package org.example.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.example.config.PasswordHelper
import org.example.model.Account
import org.example.model.Role
import org.example.repository.AuthRepository

@ApplicationScoped
class AuthService(private val securityHelper: PasswordHelper, private val authRepository: AuthRepository) {

    private fun getUserByUsername(username: String): Account? {
        return try {
            authRepository.getUserByUsername(username)
        } catch (x: Exception) {
            // log error x.message!!
            null
        }
    }

    @Transactional
    fun save(account: Account): Account? {
        account.password = securityHelper.encodePwd(account.password)
        account.authorities = setOf(Role.USER)
        authRepository.persist(account)
        return account
    }

    private fun isUserValid(username: String, password: String): Boolean {
        val user = getUserByUsername(username)
        return (user != null && securityHelper.isPasswordValid(password, user.password))
    }

    fun authenticate(username: String, password: String): Boolean {
        try {
            val user = getUserByUsername(username)
            return (user != null && isUserValid(username, password))

        } catch (x: Exception) {
            // log error x.message!!
        }
        return false
    }

    fun isUsernameTaken(username: String): Boolean {
        return getUserByUsername(username) != null
    }

    private fun getUserRoles(username: String): List<Role> {
        return authRepository.getRolesByUsername(username)
    }

    fun getUserGroups(username: String) = getUserRoles(username).map { it.name }
}