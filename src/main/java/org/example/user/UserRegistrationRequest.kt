package org.example.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@RegisterForReflection
data class UserRegistrationRequest(

    @field:JsonProperty("id")
    val id: Long,

    @field:JsonProperty("username")
    @field:NotBlank
    val username: String,

    @field:JsonProperty("email")
    @field:Email
    val email: String,

    @field:JsonProperty("password")
    @field:NotBlank
    val password: String,
) {
    fun toEntity() = User(
        id = id,
        username = username,
        email = email,
        password = password
    )
}