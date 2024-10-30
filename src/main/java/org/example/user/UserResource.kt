package org.example.user

import jakarta.annotation.security.PermitAll
import jakarta.annotation.security.RolesAllowed
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.PUT
import jakarta.ws.rs.Path
import jakarta.ws.rs.Priorities.USER
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.Status.CREATED
import jakarta.ws.rs.core.Response.Status.OK
import jakarta.ws.rs.core.Response.ok
import jakarta.ws.rs.core.SecurityContext
import jakarta.ws.rs.core.UriBuilder.fromResource
import org.example.security.Role.ADMIN
import org.example.utils.ValidationMessages.Companion.REQUEST_BODY_MUST_NOT_BE_NULL
import org.example.web.Routes.USERS_PATH
import org.example.web.Routes.USER_PATH

@Path("/")
class UserResource(
    private val service: UserService,
) {

    @POST
    @Path(USERS_PATH)
    @Transactional
    @Consumes(APPLICATION_JSON)
    @PermitAll
    fun register(
        @Valid @NotNull(message = REQUEST_BODY_MUST_NOT_BE_NULL) newUser: UserRegistrationRequest,
    ): Response = service.register(newUser).run {
        ok(this).status(CREATED)
            .location(fromResource(UserResource::class.java).path("$USERS_PATH/$username").build())
            .build()
    }

    @POST
    @Path("$USERS_PATH/login")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @PermitAll
    fun login(
        @Valid @NotNull(message = REQUEST_BODY_MUST_NOT_BE_NULL) userLoginRequest: UserLoginRequest,
    ): Response = ok(service.login(userLoginRequest)).status(OK).build()

    @GET
    @Path(USER_PATH)
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed(USER.toString())
    fun getLoggedInUser(
        @Context securityContext: SecurityContext,
    ): Response = ok(service.get(securityContext.userPrincipal.name)).status(OK).build()

    @PUT
    @Path(USER_PATH)
    @Transactional
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @RolesAllowed(USER.toString(), ADMIN)
    fun updateLoggedInUser(
        @Context securityContext: SecurityContext,
        @Valid @NotNull(message = REQUEST_BODY_MUST_NOT_BE_NULL) userUpdateRequest: UserUpdateRequest,
    ): Response = ok(service.update(securityContext.userPrincipal.name, userUpdateRequest)).status(OK).build()
}