package org.example.model

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@ApplicationScoped
@Entity
open class BaseModel(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id: Long? = 0,

    open var createdAt: LocalDateTime? = LocalDateTime.now(),
    open var updatedAt: LocalDateTime? = LocalDateTime.now()
)