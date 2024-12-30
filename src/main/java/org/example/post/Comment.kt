package org.example.post

import jakarta.persistence.*
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Size
import org.example.user.User
import org.example.utils.Tables.COMMENT_TABLE
import java.time.Instant
import java.time.Instant.now

@Entity
@Table(name = COMMENT_TABLE)
class Comment(
    @Id
    @GeneratedValue
    var id: Long = 0,

    @field:Size(min = 0, max = 1023)
    var body: String = "",

    @field:PastOrPresent
    var createdAt: Instant = now(),

    @field:PastOrPresent
    var updatedAt: Instant = now(),

    @ManyToOne
    var author: User = User(),

    @ManyToOne
    var article: Post = Post(),
)