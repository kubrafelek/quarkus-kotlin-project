package org.example.model

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@ApplicationScoped
@Entity
open class User(

    open var firstName: String? = null,
    open var lastName: String? = null,
    open var username: String? = null,
    open var email: String? = null,
    open var password: String? = null,
    open var adminRole: String? = null,

    ) : BaseModel() {
    @OneToMany(targetEntity = Post::class, cascade = [CascadeType.ALL])
    @JoinColumn(referencedColumnName = "id", name = "user_id")
    open var posts: List<Post>? = null
}