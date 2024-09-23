package org.example.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.OffsetDateTime

@Entity
class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    lateinit var title: String
    lateinit var body: String
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime

    @ManyToOne
    lateinit var account: Account
}