package authuser.core.user.rest.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.User
import authuser.core.user.data.UserSearchRequest
import authuser.core.user.data.UserUpdateRequest
import authuser.core.user.data.UserUpdateRequest.UserView.Companion.ImagePut
import authuser.core.user.data.UserUpdateRequest.UserView.Companion.PasswordPut
import authuser.core.user.data.UserUpdateRequest.UserView.Companion.UserPut
import authuser.core.user.service.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort.Direction.ASC
import org.springframework.data.web.PageableDefault
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserRestV1(
    val userService: UserService
) {

    @GetMapping
    fun findAll(
        searchRequest: UserSearchRequest,
        @PageableDefault(page = 0, size = 10, sort = ["userId"], direction = ASC) page: Pageable
    ): RestResponse<Page<User>> {

        val users = userService.findAll(searchRequest, page)

        if (users.isEmpty.not()) {
            for (user in users.toList()) {
                user.add(
                    linkTo(methodOn(UserRestV1::class.javaObjectType).find(user.userId!!)).withSelfRel()
                )
            }
        }

        return RestResponse("Users was collected", users)
    }

    @GetMapping("/{userId}")
    fun find(@PathVariable(value = "userId") userId: UUID): RestResponse<User?> {

        val user = userService.findById(userId)
        return RestResponse("user was collected", user)
    }

    @DeleteMapping("/{userId}")
    fun remove(@PathVariable(value = "userId") userId: UUID): RestResponse<Any> {

        userService.delete(userId)
        return RestResponse("User deleted successful")
    }

    @PutMapping("/{userId}")
    fun update(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody @JsonView(UserPut::class) request: UserUpdateRequest
    ): RestResponse<Any> {

        val user = userService.update(userId, request)
        return RestResponse("User was updated successful", response = user)
    }

    @PutMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody @JsonView(PasswordPut::class) request: UserUpdateRequest
    ): RestResponse<Any> {

        userService.updatePassword(userId, request)
        return RestResponse("Password updated successfully.")
    }

    @PutMapping("/{userId}/image")
    fun updateImage(
        @PathVariable(value = "userId") userId: UUID,
        @RequestBody @JsonView(ImagePut::class) request: UserUpdateRequest
    ): RestResponse<Any> {

        val user = userService.updateImage(userId, request)
        return RestResponse("Image updated successfully.", response = user)
    }
}