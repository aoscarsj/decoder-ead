package authuser.core.user.rest.external.v1

import authuser.common.rest.RestResponse
import authuser.core.user.helper.CourseHelper
import authuser.integration.service.course.data.Course
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserCourseRestV1(
    private val courseHelper: CourseHelper,
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
}