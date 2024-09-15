package org.example.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Post(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    val title: String?,
    val body: String?,

    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    val user: User?

    ) {
    constructor() : this(null, null, null, LocalDateTime.now(), LocalDateTime.now(), null)
    constructor(title: String?, body: String?, user: User?) : this(null, title, body, LocalDateTime.now(), LocalDateTime.now(), user)
}


