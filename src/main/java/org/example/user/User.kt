package org.example.user

import jakarta.persistence.*
import org.example.post.Post
import org.example.utils.Tables.USER_TABLE

@Entity
@Table(name = USER_TABLE)
class User() {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = ""

    @Column(unique = true)
    lateinit var username: String

    @Column(unique = true)
    lateinit var email: String

    lateinit var password: String

    @Enumerated(EnumType.STRING)
    lateinit var role: Role

    @OneToMany(mappedBy = "author", orphanRemoval = true)
    var posts: MutableList<Post> = mutableListOf()
}