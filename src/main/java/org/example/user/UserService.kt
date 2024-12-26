package org.example.user

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.example.exception.*
import org.example.security.BCryptHashProvider
import org.example.security.JwtTokenProvider
import org.jboss.logging.Logger

@Transactional
@ApplicationScoped
class UserService @Inject constructor(
    private val repository: UserRepository,
    private val tokenProvider: JwtTokenProvider,
    private val hashProvider: BCryptHashProvider,
) {

    private val log = Logger.getLogger(UserService::class.java)

    fun register(newUser: UserRegistrationRequest): UserResponse {
        if (repository.existsByUsername(newUser.username)) throw UsernameAlreadyExistsException()
        if (repository.existsByEmail(newUser.email)) throw EmailAlreadyExistsException()

        val userEntity = newUser.toEntity()
        userEntity.password = hashProvider.hash(newUser.password)
        repository.persist(userEntity)
        log.info("User registered in: ${userEntity.email}")
        return UserResponse.build(userEntity, tokenProvider.create(newUser.username))
    }

    fun findByUsername(username: String): User = repository.findByUsername(username)

    fun login(userLoginRequest: UserLoginRequest): UserResponse {
        val user = repository.findByEmail(userLoginRequest.email)
            ?: throw UnregisteredEmailException()

        if (!hashProvider.verify(userLoginRequest.password, user.password)) {
            throw InvalidPasswordException()
        }
        log.info("User logged in: ${user.email}")
        return UserResponse.build(user, tokenProvider.create(user.username))
    }

    fun update(loggedInUserId: String, updateRequest: UserUpdateRequest): UserResponse {
        val user = repository.findById(loggedInUserId)
            ?: throw UserNotFoundException()

        if (updateRequest.username != null &&
            updateRequest.username != user.username &&
            repository.existsByUsername(updateRequest.username)
        ) {
            throw UsernameAlreadyExistsException()
        }

        if (updateRequest.email != null &&
            updateRequest.email != user.email &&
            repository.existsByEmail(updateRequest.email)
        ) {
            throw EmailAlreadyExistsException()
        }

        val updatedUser = updateRequest.applyChangesTo(user)

        if (updateRequest.password != null) {
            updatedUser.password = hashProvider.hash(updatedUser.password)
        }
        val savedUser = repository.getEntityManager().merge(updatedUser)
        log.info("User updated: ${savedUser.email}")
        return UserResponse.build(savedUser, tokenProvider.create(savedUser.username))
    }
}