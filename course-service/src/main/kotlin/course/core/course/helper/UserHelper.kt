package course.core.course.helper

import course.common.rest.RestException
import course.core.course.exception.CourseException
import course.integrations.service.authuser.client.AuthUserClient
import course.integrations.service.authuser.data.User
import course.integrations.service.authuser.data.UserSubscriptionRequest
import feign.FeignException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserHelper(
    private val userClient: AuthUserClient
) {
    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    fun findUsersByCourse(courseId: UUID, page: Pageable): Page<User> {

        return try {
            userClient.findAllUsersByCourse(courseId, page).response ?: throw CourseException(
                "User not found by course #$courseId", NOT_FOUND
            )
        } catch (e: Exception) {

            logger.error("Error in finding users by course {}", e.stackTrace)
            throw e
        }
    }

    fun sendSubscription(userId: UUID, courseId: UUID) {

        try {
            userClient.sendSubscription(userId, UserSubscriptionRequest(courseId))
        } catch (e: Exception) {

            logger.error("Error in sending subscription {}", e.stackTrace)
            throw e
        }
    }

    fun findUser(userId: UUID): User = try {
        val userRequest = userClient.findUser(userId)
        with(userRequest) {
            response ?: throw RestException(NOT_FOUND, "User not found")
        }
    } catch (e: FeignException) {

        logger.error("Error in fetching user #$userId {}", e.localizedMessage)
        if (e.localizedMessage.contains("NOT_FOUND"))
            throw RestException(NOT_FOUND, "Error: User not found.")
        throw e
    } catch (e: Exception) {

        logger.error("Error in fetching user #$userId", e.stackTrace)
        throw e
    }
}