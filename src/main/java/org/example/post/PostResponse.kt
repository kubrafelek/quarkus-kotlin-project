package org.example.post

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import io.quarkus.runtime.annotations.RegisterForReflection
import java.time.Instant

@RegisterForReflection
data class PostResponse(
    @JsonProperty("slug")
    val slug: String,

    @JsonProperty("title")
    val title: String,

    @JsonProperty("description")
    val description: String,

    @JsonProperty("body")
    val body: String,

    @JsonProperty("createdAt")
    @JsonFormat
    val createdAt: Instant,

    @JsonProperty("updatedAt")
    @JsonFormat // FIXME: pattern is failing to pass tests "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val updatedAt: Instant,

    ) {
    companion object {
        @JvmStatic
        fun build(post: Post): PostResponse = PostResponse(
            slug = post.slug.toString(),
            title = post.title,
            description = post.description,
            body = post.body,
            createdAt = post.createdAt,
            updatedAt = post.updatedAt
        )
    }
}