package course.core.course.rest.internal.v1

import course.common.rest.RestResponse
import course.core.course.data.CourseUser
import course.core.course.data.request.CourseSubscriptionRequest
import course.core.course.service.CourseService
import course.core.course.service.CourseUserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("internal/courses")
class CourseUserInternalRestV1(
    private val courseService: CourseService,
    private val courseUserService: CourseUserService
) {
    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    @PostMapping("/{courseId}/users/subscription")
    fun saveSubscriptionUserInCourse(
        @PathVariable courseId: UUID,
        @RequestBody @Valid subscriptionRequest: CourseSubscriptionRequest
    ): RestResponse<CourseUser> {

        logger.info("Starting save subscription user in course")
        val course = courseService.find(courseId)

        return RestResponse(
            "Subscription created successfully", httpStatus = HttpStatus.CREATED,
            response = courseUserService.insert(course, subscriptionRequest, false)
        )
    }
}