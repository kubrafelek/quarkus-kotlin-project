package org.example.resource

import io.quarkus.security.identity.SecurityIdentity
import jakarta.annotation.security.RolesAllowed
import jakarta.transaction.Transactional
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.example.model.Post
import org.example.repository.PostRepository

@Path("/posts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class PostResource(
    private val postRepository: PostRepository,
    private val securityIdentity: SecurityIdentity,
) {

    @GET
    @RolesAllowed("user")
    fun getAll(): List<Post> {
        return postRepository.listAll()
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user")
    fun getById(@PathParam("id") id: Long): Post {
        return postRepository.findById(id) ?: throw WebApplicationException("Post not found", Response.Status.NOT_FOUND)
    }

    @POST
    @Transactional
    @RolesAllowed("admin")
    fun create(post: Post): Response {
        val userIdClaim = securityIdentity.principal.name // Get the userId claim
        // Further validation or logging based on custom claims can be added here
        postRepository.persist(post)
        return Response.ok(post).status(Response.Status.CREATED).build()
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("user")
    fun update(@PathParam("id") id: Long, post: Post): Response {
        val entity =
            postRepository.findById(id) ?: throw WebApplicationException("Post not found", Response.Status.NOT_FOUND)
        val jwtUserId = securityIdentity.principal.name

        // Allow update only if the userId from JWT matches the post owner's ID
        if (jwtUserId != entity.id.toString()) {
            throw WebApplicationException("User is not authorized to update this post", Response.Status.FORBIDDEN)
        }

        entity.title = post.title
        entity.body = post.body
        postRepository.persist(entity)
        return Response.ok(entity).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin")
    fun delete(@PathParam("id") id: Long): Response {
        val entity =
            postRepository.findById(id) ?: throw WebApplicationException("Post not found", Response.Status.NOT_FOUND)
        postRepository.delete(entity)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}