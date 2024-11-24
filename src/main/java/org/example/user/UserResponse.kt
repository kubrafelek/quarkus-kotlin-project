package org.example.user

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class UserResponse(
    @JsonProperty("id")
    var id: String,

    @JsonProperty("username")
    var username: String,

    @JsonProperty("email")
    var email: String,

    @JsonProperty("token")
    var token: String,

    ) {
    companion object {
        fun build(user: User, token: String): UserResponse = UserResponse(
            id = user.id,
            username = user.username,
            email = user.email,
            token = token,
        )
    }
}