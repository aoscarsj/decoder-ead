package authuser.core.user.rest.internal.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.UserCourse
import authuser.core.user.data.request.UserSubscriptionRequest
import authuser.core.user.service.UserCourseService
import authuser.core.user.service.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/internal/users")
class UserCourseInternalV1(
    private val userCourseService: UserCourseService,
    private val userService: UserService
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    @PostMapping("/{userId}/courses/subscription")
    fun saveSubscription(
        @PathVariable userId: UUID, @RequestBody subscriptionRequest:
        UserSubscriptionRequest
    ): RestResponse<UserCourse> {

        logger.info("Starting save subscription course in user")
        val user = userService.find(userId)

        return RestResponse(
            "Subscription created successfully", httpStatus = HttpStatus.CREATED, response =
            userCourseService.insert(user, subscriptionRequest, false)
        )
    }
}