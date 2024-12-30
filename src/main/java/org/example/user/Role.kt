package org.example.user

import com.fasterxml.jackson.annotation.JsonValue
import io.quarkus.runtime.annotations.RegisterForReflection

@RegisterForReflection
enum class Role(@JsonValue val value: String) {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    override fun toString(): String {
        return value
    }

    companion object {
        fun fromString(role: String): Role? {
            return entries.find { it.name.equals(role, ignoreCase = true) }
        }
    }
}