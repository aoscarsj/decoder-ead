package authuser.core.user.rest.external.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.User
import authuser.core.user.data.request.UserSearchRequest
import authuser.core.user.data.request.UserUpdateRequest
import authuser.core.user.data.request.UserUpdateRequest.UserView.Companion.ImagePut
import authuser.core.user.data.request.UserUpdateRequest.UserView.Companion.PasswordPut
import authuser.core.user.data.request.UserUpdateRequest.UserView.Companion.UserPut
import authuser.core.user.extension.insertHateoasLink
import authuser.core.user.service.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
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
        searchRequest: UserSearchRequest, @PageableDefault page: Pageable
    ): RestResponse<Page<User>> {

        val users = userService.findAll(searchRequest, page).insertHateoasLink()

        return RestResponse("Users was collected", users)
    }

    @GetMapping("/courses/{courseId}")
    fun findAllByCourse(
        @PathVariable courseId: UUID, @PageableDefault page: Pageable
    ): RestResponse<Page<User>> {

        val users = userService.findAllByCourse(courseId, page)
        return RestResponse("Users was collected", users)
    }

    @GetMapping("/{userId}")
    fun find(@PathVariable userId: UUID): RestResponse<User?> {

        val user = userService.find(userId)
        return RestResponse("user was collected", user)
    }

    @DeleteMapping("/{userId}")
    fun remove(@PathVariable userId: UUID): RestResponse<Any> {

        userService.delete(userId)
        return RestResponse("User deleted successful")
    }

    @PutMapping("/{userId}")
    fun update(
        @PathVariable userId: UUID,
        @RequestBody @JsonView(UserPut::class) request: UserUpdateRequest
    ): RestResponse<Any> {

        val user = userService.update(userId, request)
        return RestResponse("User was updated successful", response = user)
    }

    @PutMapping("/{userId}/password")
    fun updatePassword(
        @PathVariable userId: UUID,
        @RequestBody @JsonView(PasswordPut::class) request: UserUpdateRequest
    ): RestResponse<Any> {

        userService.updatePassword(userId, request)
        return RestResponse("Password updated successfully.")
    }

    @PutMapping("/{userId}/image")
    fun updateImage(
        @PathVariable userId: UUID,
        @RequestBody @JsonView(ImagePut::class) request: UserUpdateRequest
    ): RestResponse<Any> {

        val user = userService.updateImage(userId, request)
        return RestResponse("Image updated successfully.", response = user)
    }
}