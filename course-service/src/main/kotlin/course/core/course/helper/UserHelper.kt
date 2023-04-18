package course.core.course.helper

import course.core.course.exception.CourseException
import course.integrations.service.authuser.client.UserClientV1
import course.integrations.service.authuser.data.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserHelper(
    private val userClient: UserClientV1
) {
    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    fun findUsersByCourse(courseId: UUID, page: Pageable): Page<User> {

        return try {
            userClient.findAllUsersByCourse(courseId, page).response ?: throw CourseException(
                "User not found by course #$courseId", httpStatus = HttpStatus.NOT_FOUND
            )
        } catch (e: Exception) {
            logger.error("Error on finding users by course", e)
            throw e
        }
    }
}