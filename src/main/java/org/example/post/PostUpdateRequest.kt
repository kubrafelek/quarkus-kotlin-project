package org.example.post

import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import java.util.*

@RegisterForReflection
data class PostUpdateRequest(
    @JsonProperty("title")
    val title: String? = null,

    @JsonProperty("description")
    val description: String? = null,

    @JsonProperty("body")
    val body: String? = null
) {
    fun applyChangesTo(existingPost: Post, newPostId: UUID = UUID.randomUUID()) = Post(
        slug = title?.let { newPostId } ?: existingPost.slug,
        title = title ?: existingPost.title,
        description = description ?: existingPost.description,
        body = body ?: existingPost.body,
        createdAt = existingPost.createdAt,
        updatedAt = existingPost.updatedAt,
        author = existingPost.author)
}
