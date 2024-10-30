package org.example.user

import jakarta.enterprise.context.ApplicationScoped
import org.example.exception.EmailAlreadyExistsException
import org.example.exception.InvalidPasswordException
import org.example.exception.UnregisteredEmailException
import org.example.exception.UserNotFoundException
import org.example.exception.UsernameAlreadyExistsException
import org.example.security.BCryptHashProvider
import org.example.security.JwtTokenProvider

@ApplicationScoped
class UserService(
    private val repository: UserRepository,
    private val tokenProvider: JwtTokenProvider,
    private val hashProvider: BCryptHashProvider,
) {
    fun get(username: String): UserResponse = repository.findById(username)?.run {
        UserResponse.build(this, tokenProvider.create(username))
    } ?: throw UserNotFoundException()

    fun register(newUser: UserRegistrationRequest): UserResponse = newUser.run {
        if (repository.existsUsername(newUser.username)) throw UsernameAlreadyExistsException()
        if (repository.existsEmail(newUser.email)) throw EmailAlreadyExistsException()

        UserResponse.build(
            this.toEntity().also {
                it.password = hashProvider.hash(password)
                repository.persist(it)
            },
            tokenProvider.create(username)
        )
    }

    fun login(userLoginRequest: UserLoginRequest) = repository.findByEmail(userLoginRequest.email)?.run {
        if (!hashProvider.verify(userLoginRequest.password, password)) throw InvalidPasswordException()
        else UserResponse.build(this, tokenProvider.create(username))
    } ?: throw UnregisteredEmailException()

    fun update(loggedInUserId: String, updateRequest: UserUpdateRequest): UserResponse = repository
        .findById(loggedInUserId)
        ?.run {
            if (updateRequest.username != null &&
                updateRequest.username != username &&
                repository.existsUsername(updateRequest.username)
            ) throw UsernameAlreadyExistsException()

            if (updateRequest.email != null &&
                updateRequest.email != email &&
                repository.existsEmail(updateRequest.email)
            ) throw EmailAlreadyExistsException()

            UserResponse.build(
                updateRequest.applyChangesTo(this).apply {
                    if (updateRequest.password != null) this.password = hashProvider.hash(password)
                    repository.persist(this)
                },
                tokenProvider.create(username)
            )
        } ?: throw UserNotFoundException()
}