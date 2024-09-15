package org.example.authentication

import jakarta.annotation.security.RolesAllowed
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.SecurityContext


@Path("/api/users")
class UserResource {

    @GET
    @RolesAllowed("user")
    @Path("/me")
    fun me(@Context securityContext: SecurityContext): String {
        return securityContext.userPrincipal.name
    }
}