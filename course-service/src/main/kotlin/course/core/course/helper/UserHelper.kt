package course.core.course.helper

import course.common.rest.RestException
import course.core.course.data.User
import course.core.course.service.UserService
import course.integrations.service.authuser.client.AuthUserClient
import course.integrations.service.authuser.data.UserSubscriptionRequest
import feign.FeignException
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class UserHelper(
    private val userClient: AuthUserClient,
    private val userService: UserService
) {
    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    fun findUsersByCourse(courseId: UUID, page: Pageable): Page<User> {
        return userService.findAllUsersInCourse(courseId, page)
    }

    @Transactional
    fun removeSubscription(courseId: UUID) = try {
        logger.info("M=removeSubscription")
        userClient.removeSubscription(courseId)
    } catch (e: FeignException) {
        logger.error("Error on remove subscription in authuser integration")
        throw RestException(INTERNAL_SERVER_ERROR, "Error in remove subscription")
    }

    fun sendSubscription(userId: UUID, courseId: UUID) {

        try {
            userClient.sendSubscription(userId, UserSubscriptionRequest(courseId))
        } catch (e: FeignException) {

            logger.error("Error in sending subscription - Feign {}", e.stackTrace)
            throw RestException(INTERNAL_SERVER_ERROR, "Error in sending subscription")
        } catch (e: Exception) {

            logger.error("Error in sending subscription {}", e.stackTrace)
            throw e
        }
    }
}