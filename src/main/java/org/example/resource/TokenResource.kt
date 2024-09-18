package org.example.resource

import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import org.example.authentication.JwtTokenGenerator

@Path("/token")
class TokenResource(private val jwtTokenGenerator: JwtTokenGenerator) {

    @GET
    @Path("/user")
    fun getUserToken(): String {
        return jwtTokenGenerator.generateToken("user", listOf("user"))
    }

    @GET
    @Path("/admin")
    fun getAdminToken(): String {
        return jwtTokenGenerator.generateToken("admin", listOf("admin"))
    }
}