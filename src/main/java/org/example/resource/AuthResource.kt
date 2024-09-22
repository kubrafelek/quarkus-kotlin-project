package org.example.resource

import io.quarkus.security.identity.SecurityIdentity
import io.smallrye.jwt.config.ConfigLogging.log
import jakarta.inject.Inject
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody
import org.example.exceptions.UsernameNotFoundException
import org.example.model.AuthRequest
import org.example.model.CreateUserRequest
import org.example.service.JwtService
import org.example.service.UserService

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class UserResource {

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var jwtService: JwtService

    @Inject
    lateinit var securityIdentity: SecurityIdentity

    @GET
    @Path("/welcome")
    fun welcome(): String {
        return "Hello World!"
    }

    @GET
    @Path("/user")
    fun getUserString(): String {
        return "This is USER!"
    }

    @GET
    @Path("/admin")
    fun getAdminString(): String {
        return "This is ADMIN!"
    }

    @POST
    @Path("/addNewUser")
    fun addUser(@RequestBody request: CreateUserRequest) {
        return userService.createUser(request)
    }

    @POST
    @Path("/generateToken")
    fun generateToken(@RequestBody request: AuthRequest): String {
        if (securityIdentity.isAnonymous) {
            log.info("username or password is null")
            throw UsernameNotFoundException("username or password is null")
        }
        return jwtService.generateToken(request.username)
    }

}