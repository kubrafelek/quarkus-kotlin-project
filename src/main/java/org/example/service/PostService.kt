package org.example.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.example.model.Post
import org.example.repository.PostRepository

@ApplicationScoped
@Transactional
class PostService {

    @Inject
    lateinit var postRepository: PostRepository

    fun getAllPosts() = postRepository.findAll().list<Post>()

    fun getPostById(id: Long) = postRepository.findById(id)

    fun createPost(post: Post) = postRepository.persist(post)

    fun updatePost(id: Long, post: Post) {
        val existingPost = postRepository.findById(id)
        existingPost?.title = post.title
        existingPost?.content = post.content
        postRepository.persist(existingPost)
    }

    fun deletePost(id: Long) = postRepository.deleteById(id)
}