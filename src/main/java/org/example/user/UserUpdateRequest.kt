package org.example.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import io.quarkus.runtime.annotations.RegisterForReflection

@JsonRootName("user")
@RegisterForReflection
data class UserUpdateRequest(
    @JsonProperty("username") val username: String? = null,

    @JsonProperty("email") val email: String? = null,

    @JsonProperty("password") val password: String? = null
) {
    fun applyChangesTo(existingUser: User) = User(
        id = existingUser.id,
        username = username ?: existingUser.username,
        email = email ?: existingUser.email,
        password = password ?: existingUser.password
    )
}