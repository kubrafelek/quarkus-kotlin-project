package org.example.resource

import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response
import org.eclipse.microprofile.openapi.annotations.Operation
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse
import org.example.config.TokenHelper
import org.example.dto.AccountDto
import org.example.model.Account
import org.example.service.AuthService

@Path("/auth")
class AuthResource(private val usersService: AuthService, private val tokenHelper: TokenHelper) {

    @POST
    @Path("/register")
    @Operation(summary = "Create a new user account")
    @APIResponse(responseCode = "201", description = "Account created")
    fun save(@Valid accountDto: AccountDto): Response {
        if (usersService.isUsernameTaken(accountDto.username)) {
            return Response.status(Response.Status.BAD_REQUEST)
                .entity("Username '${accountDto.username}' already taken.")
                .build()
        }
        val account = AccountDto.toDto(accountDto)
        val result = usersService.save(account)
        return if (result != null) {
            Response.status(Response.Status.CREATED).entity("created $result").build()
        } else Response.status(Response.Status.BAD_REQUEST).entity("unable to create user!").build()

        @POST
        @Path("/login")
        fun login(@Valid user: Account): Response {
            return if (usersService.authenticate(user.username, user.password)) {
                val token = tokenHelper.getToken(user)
                Response.ok(token).build()
            } else Response.status(Response.Status.BAD_REQUEST).entity("Invalid credentials!").build()
        }
    }
}