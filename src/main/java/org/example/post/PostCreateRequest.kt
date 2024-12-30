package org.example.post

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import org.example.user.User
import java.time.Instant.now
import java.util.*
import java.util.UUID.randomUUID

@RegisterForReflection
data class PostCreateRequest(

    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("body")
    val body: String,
) {
    fun toEntity(authorId: String, articleId: UUID = randomUUID()) = Post(
        slug = articleId,
        title = title,
        description = description,
        body = body,
        createdAt = now(),
        updatedAt = now(),
        author = User(),
        comments = mutableListOf()
    )
}
