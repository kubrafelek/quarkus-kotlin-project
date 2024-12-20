package org.example.user

import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.Priorities.USER
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.SecurityContext
import org.example.web.Routes.USERS_PATH
import org.example.web.Routes.USER_PATH

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path("/")
class UserResource @Inject constructor(
    private val service: UserService,
) {

    @POST
    @Path("$USERS_PATH/register")
    @PermitAll
    @Transactional
    fun registerUser(@Valid userRegistrationRequest: UserRegistrationRequest): Response {
        return try {
            val registeredUser = service.register(userRegistrationRequest)
            Response.ok(registeredUser).build()
        } catch (e: IllegalArgumentException) {
            Response.status(Response.Status.BAD_REQUEST).entity(e.message).build()
        }
    }

    @POST
    @Path("$USERS_PATH/login")
    @PermitAll
    fun login(
        @Valid userLoginRequest: UserLoginRequest,
    ): Response = Response
        .ok(service.login(userLoginRequest))
        .status(Response.Status.OK)
        .build()

    @GET
    @Path(USER_PATH)
    @RolesAllowed(USER.toString())
    fun getLoggedInUser(
        @Context securityContext: SecurityContext,
    ): Response = Response
        .ok(service.findByUsername(securityContext.userPrincipal.name))
        .status(Response.Status.OK)
        .build()
}