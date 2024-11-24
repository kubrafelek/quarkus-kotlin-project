package org.example.user

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.eclipse.microprofile.openapi.annotations.media.Schema
import org.example.utils.Tables.USER_TABLE

@Entity
@Table(name = USER_TABLE)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(required = true, readOnly = true)
    var id: Long? = null,

    @field:NotBlank(message = "Username cannot be blank") @field:Size(
        min = 3,
        max = 50,
        message = "Username must be between 3 and 50 characters"
    )
    @Column(unique = true, nullable = false)
    var username: String,

    @field:NotBlank(message = "Email cannot be blank")
    @field:Email(message = "Invalid email format")
    @Column(
        unique = true,
        nullable = false
    )
    var email: String,

    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(
        min = 8,
        message = "Password must be at least 8 characters"
    )
    @Column(nullable = false)
    var password: String,
) {
}