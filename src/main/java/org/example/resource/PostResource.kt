package org.example.resource

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
class PostResource(private val postRepository: PostRepository) {

    @GET
    @RolesAllowed("user") // Allow users with the "user" role to access this
    fun getAll(): List<Post> {
        return postRepository.listAll()
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("user") // Allow users with the "user" role to access this
    fun getById(@PathParam("id") id: Long): Post {
        return postRepository.findById(id) ?: throw WebApplicationException("Post not found", Response.Status.NOT_FOUND)
    }

    @POST
    @Transactional
    @RolesAllowed("admin") // Only admins can create posts
    fun create(post: Post): Response {
        postRepository.persist(post)
        return Response.ok(post).status(Response.Status.CREATED).build()
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin") // Only admins can update posts
    fun update(@PathParam("id") id: Long, post: Post): Response {
        val entity =
            postRepository.findById(id) ?: throw WebApplicationException("Post not found", Response.Status.NOT_FOUND)

        entity.title = post.title
        entity.body = post.body
        postRepository.persist(entity)
        return Response.ok(entity).build()
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @RolesAllowed("admin") // Only admins can delete posts
    fun delete(@PathParam("id") id: Long): Response {
        val entity =
            postRepository.findById(id) ?: throw WebApplicationException("Post not found", Response.Status.NOT_FOUND)
        postRepository.delete(entity)
        return Response.status(Response.Status.NO_CONTENT).build()
    }
}