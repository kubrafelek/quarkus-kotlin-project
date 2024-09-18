package org.example.resource

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.QueryParam
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.example.authentication.JwtTokenGenerator

@Path("/token")
class TokenResource(private val jwtTokenGenerator: JwtTokenGenerator) {

    @GET
    @Path("/generate")
    @Produces(MediaType.TEXT_PLAIN)
    fun generateToken(
        @QueryParam("username") username: String,
        @QueryParam("userId") userId: String,
        @QueryParam("roles") roles: List<String>
    ): Response {
        // Generate the JWT token with custom claims
        val token = jwtTokenGenerator.generateToken(username, roles, userId)
        return Response.ok(token).build()
    }
}