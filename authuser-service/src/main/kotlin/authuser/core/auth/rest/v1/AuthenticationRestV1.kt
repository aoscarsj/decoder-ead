package authuser.core.auth.rest.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.User
import authuser.core.user.data.UserCreateRequest
import authuser.core.user.data.UserCreateRequest.UserView.Companion.RegistrationPost
import authuser.core.user.service.UserService
import com.fasterxml.jackson.annotation.JsonView
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/auth")
class AuthenticationRestV1(
    private val userService: UserService,
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    @PostMapping("/signup")
    fun registerUser(
        @RequestBody @JsonView(RegistrationPost::class) userRequest: UserCreateRequest
    ): RestResponse<User> {

        logger.debug("POST registerUser userRequest received $userRequest")

        val createdUser = userService.signup(userRequest)

        logger.debug("POST registerUser return new user $createdUser")
        logger.info("User saved successfully userId: ${createdUser.userId}")
        return RestResponse(
            message = "User was successfully created", response = createdUser,
            httpStatus = HttpStatus.CREATED
        )
    }
}