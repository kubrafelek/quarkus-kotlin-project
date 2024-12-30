package org.example.post

import jakarta.enterprise.context.ApplicationScoped
import org.example.exception.PostNotFoundException
import java.util.*

@ApplicationScoped
class PostService(private val postRepository: PostRepository) {

    fun create(createRequest: PostCreateRequest, loggedInUserId: String): PostResponse {
        val createRequest = createRequest.toEntity(loggedInUserId)
        postRepository.persist(createRequest)
        PostResponse.build(createRequest)
        return PostResponse.build(createRequest)
    }

    fun update(articleId: UUID, updateRequest: PostUpdateRequest): PostResponse {
        val existingPost = postRepository.findById(articleId) ?: throw PostNotFoundException()
        updateRequest.applyChangesTo(existingPost)
        postRepository.persist(existingPost)
        return PostResponse.build(existingPost)
    }

    fun delete(articleId: UUID): Boolean {
        val deleted = postRepository.deleteById(articleId)
        if (!deleted) throw PostNotFoundException()
        return postRepository.deleteById(articleId)
    }

}