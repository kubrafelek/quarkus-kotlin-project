package org.example.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class UserUpdateRequest(
    @JsonProperty("username") val username: String? = null,

    @JsonProperty("email") val email: String? = null,

    @JsonProperty("password") val password: String? = null
) {
    fun applyChangesTo(user: User): User {
        username?.let { user.username = it }
        email?.let { user.email = it }
        password?.let { user.password = it }
        return user
    }
}