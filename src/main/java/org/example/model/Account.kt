package org.example.model

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
class Account{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null

    lateinit var username: String
    lateinit var password: String
    lateinit var createdAt: OffsetDateTime
    lateinit var updatedAt: OffsetDateTime

    @ElementCollection(targetClass = Role::class)
    @CollectionTable(
        name = "authorities",
        joinColumns = [JoinColumn(name = "account_id")]
    )
    @Enumerated(EnumType.STRING)
    var authorities: Set<Role> = HashSet()

    @OneToMany(mappedBy = "account")
    var posts: Set<Post> = HashSet()
}
