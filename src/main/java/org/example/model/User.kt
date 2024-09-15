package org.example.model

import io.quarkus.elytron.security.common.BcryptUtil
import io.quarkus.hibernate.orm.panache.PanacheEntity
import io.quarkus.security.jpa.UserDefinition
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@UserDefinition
data class User(

    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long? = 0,

    val username: String?,
    val password: String?,

    val createdAt: LocalDateTime? = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = LocalDateTime.now(),

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER) @CollectionTable(
        name = "authorities",
        joinColumns = [JoinColumn(name = "user_id")]
    ) @Enumerated(EnumType.STRING) val authorities: Set<Role> = HashSet(),

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = [CascadeType.ALL]) val posts: Set<Post> = HashSet(),
) : PanacheEntity() {
    constructor() : this(null, null, null, LocalDateTime.now(), LocalDateTime.now(), emptySet())
    constructor(username: String?, password: String?) : this(
        null,
        username,
        BcryptUtil.bcryptHash(password),
        LocalDateTime.now(),
        LocalDateTime.now(),
        emptySet(),
        emptySet()
    )
}