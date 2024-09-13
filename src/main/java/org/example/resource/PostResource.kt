package org.example.resource

import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.example.model.Post
import org.example.service.PostService

@Path("/posts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class PostResource {

     @Inject
     lateinit var postService: PostService

     @Path("/all")
     @GET
     fun getAllPosts(): MutableList<Post> {
         return postService.getAllPosts()
     }

     @GET
     @Path("/{id}")
     fun getPost(@PathParam("id") id: Long): Post {
         return postService.getPostById(id)
     }

     @POST
     fun createPost(post: Post): Response {
         postService.createPost(post)
         return Response.ok().build()
     }

     @PUT
     @Path("/{id}")
     fun updatePost(@PathParam("id") id: Long, post: Post): Response {
         postService.updatePost(id, post)
         return Response.ok().build()
     }

     @DELETE
     @Path("/{id}")
     fun deletePost(@PathParam("id") id: Long): Response {
         postService.deletePost(id)
         return Response.ok().build()

    }
}