package org.example.post

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.handler.codec.http.HttpResponseStatus.CREATED
import io.smallrye.common.constraint.NotNull
import jakarta.annotation.security.PermitAll
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import jakarta.ws.rs.*
import jakarta.ws.rs.core.Context
import jakarta.ws.rs.core.MediaType.APPLICATION_JSON
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.Response.ok
import jakarta.ws.rs.core.SecurityContext
import jakarta.ws.rs.core.UriBuilder
import org.example.exception.InvalidAuthorException
import org.example.user.Role
import org.example.utils.ValidationMessages.Companion.REQUEST_BODY_MUST_NOT_BE_NULL
import org.example.web.Routes.POSTS_PATH
import java.util.*

@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Path(POSTS_PATH)
class PostResource @Inject constructor(
    private val objectMapper: ObjectMapper, private val service: PostService
) {

    @GET
    @PermitAll
    fun list(
        @QueryParam("limit") @DefaultValue("20") limit: Int = 20,
        @QueryParam("offset") @DefaultValue("0") offset: Int = 0,
        @QueryParam("tag") tags: List<String> = listOf(),
        @QueryParam("author") authors: List<String> = listOf(),
        @Context securityContext: SecurityContext?
    ): Response = ok(
        objectMapper.writeValueAsString(
            service.list(limit, offset, tags, authors, securityContext?.userPrincipal?.name)
        )
    ).build()

    @GET
    @Path("/feed")
    @PermitAll
    fun feed(
        @QueryParam("limit") @DefaultValue("20") limit: Int = 20,
        @QueryParam("offset") @DefaultValue("0") offset: Int = 0,
        @Context securityContext: SecurityContext
    ): Response = ok(
        service.feed(limit, offset, securityContext.userPrincipal.name)
    ).build()

    @GET
    @Path("/{slug}")
    @Produces(APPLICATION_JSON)
    @PermitAll
    fun get(
        @PathParam("slug") slug: UUID,
        @Context securityContext: SecurityContext
    ): Response = ok(
        service.get(slug, securityContext.userPrincipal.name)
    ).build()

    @POST
    @Transactional
    @PermitAll
    fun create(
        @Valid @NotNull(message = REQUEST_BODY_MUST_NOT_BE_NULL) newPost: PostCreateRequest,
        @Context securityContext: SecurityContext,
    ): Response = service.create(newPost, securityContext.userPrincipal.name).run {
        ok(this)
            .status(CREATED)
            .location(UriBuilder.fromResource(PostResource::class.java).path("/$slug").build())
            .build()
    }

    @PUT
    @Path("/{slug}")
    @Transactional
    @PermitAll
    fun update(
        @PathParam("slug") slug: UUID,
        @Valid @NotNull(message = REQUEST_BODY_MUST_NOT_BE_NULL) updateRequest: PostUpdateRequest,
        @Context securityContext: SecurityContext
    ): Response = securityContext.run {
        if (service.isArticleAuthor(slug, userPrincipal.name) || isUserInRole(Role.ADMIN)) {
            ok(service.update(slug, updateRequest)).build()
        } else throw InvalidAuthorException()
    }

    @DELETE
    @Path("/{slug}")
    @Transactional
    @PermitAll
    fun delete(
        @PathParam("slug") slug: UUID,
        @Context securityContext: SecurityContext
    ): Response = securityContext.run {
        if (service.isArticleAuthor(slug, userPrincipal.name) || isUserInRole(Role.ADMIN)) {
            ok(service.delete(slug)).build()
        } else throw InvalidAuthorException()
    }
}