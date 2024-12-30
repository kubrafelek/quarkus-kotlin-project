package org.example.post

import io.quarkus.hibernate.orm.panache.kotlin.PanacheRepositoryBase
import jakarta.enterprise.context.ApplicationScoped
import java.util.*

@ApplicationScoped
class PostRepository : PanacheRepositoryBase<Post, UUID> {

    private fun List<String>.toUpperCase(): List<String> = this.map(String::toUpperCase)
}