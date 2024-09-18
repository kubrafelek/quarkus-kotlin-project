package org.example.repository

import io.quarkus.hibernate.orm.panache.PanacheRepository
import jakarta.enterprise.context.ApplicationScoped
import org.example.model.Post

@ApplicationScoped
class PostRepository : PanacheRepository<Post> {
    fun findByTitle(title: String) = find("title", title).firstResult<Post>()
}