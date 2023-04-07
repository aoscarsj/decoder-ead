package authuser.core.user.helper

import authuser.core.user.exception.UserException
import authuser.integration.service.course.client.CourseClientV1
import authuser.integration.service.course.data.Course
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class CourseHelper(
    private val courseClient: CourseClientV1
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }


    fun findCoursesByUser(userId: UUID, page: Pageable):
            Page<Course> {

        return try {
            courseClient.findAllCoursesByUser(userId, page).response ?: throw UserException(
                "Course not found by user #$userId", httpStatus = HttpStatus.NOT_FOUND
            )
        } catch (e: Exception) {
            logger.error("Error on finding courses by user, $e")
            throw e
        }
    }

}