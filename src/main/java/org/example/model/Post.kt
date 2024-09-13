package org.example.model

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


@ApplicationScoped
@Entity
open class Post(

    @NotBlank(message = "Please enter post title")
    open var title: String? = null,
    @Column(columnDefinition = "TEXT")
    open var content: String? = null,
    open var imageFilePath: String? = null,

): BaseModel() {
    @NotNull
    @ManyToOne
    @JsonIgnore
    open var user: User? = null
}


