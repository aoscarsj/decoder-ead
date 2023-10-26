package authuser.core.user.rest.external.v1

import authuser.common.rest.RestResponse
import authuser.core.user.data.UserCourse
import authuser.core.user.data.request.UserSubscriptionRequest
import authuser.core.user.helper.CourseHelper
import authuser.core.user.service.UserCourseService
import authuser.core.user.service.UserService
import authuser.integration.service.course.data.Course
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserCourseRestV1(
    private val courseHelper: CourseHelper,
    private val userCourseService: UserCourseService,
    private val userService: UserService
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    @GetMapping("/{userId}/courses")
    fun findAllCoursesByUsers(
        @PathVariable userId: UUID, @PageableDefault page: Pageable
    ): RestResponse<Page<Course>> {

        logger.info("Starting searches for courses by user #$userId")
        return RestResponse(
            "Courses was collected", response = courseHelper.findCoursesByUser(userId, page)
        )
    }

    @PostMapping("/{userId}/courses/subscription")
    fun saveSubscription(
        @PathVariable userId: UUID, @RequestBody subscriptionRequest:
        UserSubscriptionRequest
    ): RestResponse<UserCourse> {

        logger.info("Starting save subscription course in user")
        val user = userService.find(userId)

        return RestResponse(
            "Subscription created successfully", httpStatus = HttpStatus.CREATED, response =
            userCourseService.insert(user, subscriptionRequest)
        )
    }
}