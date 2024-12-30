package org.example.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@RegisterForReflection
data class UserRegistrationRequest(

    @field:JsonProperty("username") @field:NotBlank val username: String,

    @field:JsonProperty("email") @field:Email val email: String,

    @field:JsonProperty("password") @field:NotBlank val password: String,
) {
    fun toEntity(): User {
        return User().apply {
            username = this@UserRegistrationRequest.username
            email = this@UserRegistrationRequest.email
            role = Role.USER
        }
    }
}