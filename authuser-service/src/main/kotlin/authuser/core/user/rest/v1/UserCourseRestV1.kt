package authuser.core.user.rest.v1

import authuser.common.rest.RestResponse
import authuser.core.user.helper.CourseHelper
import authuser.integration.service.course.data.Course
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class UserCourseRestV1(
    private val courseHelper: CourseHelper
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    @GetMapping("/users/{userId}/courses")
    fun findAllCoursesByUsers(
        @PathVariable(value = "userId") userId: UUID,
        @PageableDefault(
            page = 0, size = 10, sort = ["userId"], direction = Sort.Direction.ASC
        ) page: Pageable
    ): RestResponse<Page<Course>> {

        logger.info("Starting searches for courses by user #$userId")
        return RestResponse(
            "Courses was collected", response = courseHelper.findCoursesByUser(userId, page)
        )
    }
}