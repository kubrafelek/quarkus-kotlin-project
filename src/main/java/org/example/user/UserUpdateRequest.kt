package org.example.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonRootName
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
data class UserUpdateRequest(
    @JsonProperty("username") val username: String? = null,

    @JsonProperty("email") val email: String? = null,

    @JsonProperty("password") val password: String? = null
) {}