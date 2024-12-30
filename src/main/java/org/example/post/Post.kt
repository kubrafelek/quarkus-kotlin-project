package org.example.post

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PastOrPresent
import jakarta.validation.constraints.Size
import org.example.user.User
import org.example.utils.Tables.POST_TABLE
import org.example.utils.ValidationMessages.Companion.TITLE_MUST_NOT_BE_BLANK
import java.time.Instant
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

@Entity
@Table(name = POST_TABLE)
class Post(
    @Id
    @Column(columnDefinition = "uuid")
    @GeneratedValue(strategy = GenerationType.UUID)
    var slug: UUID = randomUUID(),

    @field:Size(min = 5, max = 127)
    @field:NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
    var title: String = "",

    @field:Size(min = 0, max = 255)
    var description: String = "",

    @field:Size(min = 0, max = 4095)
    var body: String = "",

    @field:PastOrPresent
    var createdAt: Instant = now(),

    @field:PastOrPresent
    var updatedAt: Instant = now(),

    @ManyToOne
    var author: User = User(),

    @OneToMany(mappedBy = "post", orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf(),
)