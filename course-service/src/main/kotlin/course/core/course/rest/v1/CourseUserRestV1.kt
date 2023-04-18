package course.core.course.rest.v1

import course.common.rest.RestResponse
import course.core.course.helper.UserHelper
import course.integrations.service.authuser.data.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/courses")
class CourseUserRestV1(
    private val userHelper: UserHelper
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    @GetMapping("/{courseId}/users")
    fun findAllUsersByCourse(@PathVariable courseId: UUID, @PageableDefault page: Pageable):
            RestResponse<Page<User>> {

        logger.info("Starting searches for users by course #$courseId")
        return RestResponse(
            "Users was collected", response = userHelper.findUsersByCourse(courseId, page)
        )
    }
}