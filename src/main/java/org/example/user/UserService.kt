package org.example.user

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.example.exception.EmailAlreadyExistsException
import org.example.exception.InvalidPasswordException
import org.example.exception.UnregisteredEmailException
import org.example.exception.UsernameAlreadyExistsException
import org.example.security.BCryptHashProvider
import org.example.security.JwtTokenProvider
import java.util.UUID.randomUUID

@Transactional
@ApplicationScoped
class UserService @Inject constructor(
    private val repository: UserRepository,
    private val tokenProvider: JwtTokenProvider,
    private val hashProvider: BCryptHashProvider,
) {

    fun register(newUser: UserRegistrationRequest): UserResponse {
        if (repository.existsByUsername(newUser.username)) throw UsernameAlreadyExistsException()
        if (repository.existsByEmail(newUser.email)) throw EmailAlreadyExistsException()

        val userEntity = newUser.toEntity()
        userEntity.password = hashProvider.hash(newUser.password)
        repository.persist(userEntity)

        return UserResponse.build(userEntity, tokenProvider.create(newUser.username))
    }

    fun findByUsername(username: String): User = repository.findByUsername(username)

    fun login(userLoginRequest: UserLoginRequest): UserResponse {
        val user = repository.findByEmail(userLoginRequest.email)
            ?: throw UnregisteredEmailException()

        if (!hashProvider.verify(userLoginRequest.password, user.password)) {
            throw InvalidPasswordException()
        }

        return UserResponse.build(user, tokenProvider.create(user.username.toString()))
    }
}