package authuser.core.user.helper

import authuser.core.user.exception.CourseIntegrationException
import authuser.core.user.exception.UserException
import authuser.integration.service.course.client.CourseClientV1
import authuser.integration.service.course.data.Course
import authuser.integration.service.course.data.request.SubscriptionRequest
import feign.FeignException
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
        } catch (e: FeignException) {
            logger.error("Error on finding courses by user, $e")
            throw CourseIntegrationException()
        }
    }

    fun sendSubscription(courseId: UUID, userId: UUID) = try {
        courseClient.saveSubscriptionUserInCourse(courseId, SubscriptionRequest(userId))
    } catch (e: FeignException) {
        logger.error("Error when sending subscription for course, courseId $courseId, userId $userId")
        throw CourseIntegrationException()
    }

    fun find(courseId: UUID): Course = try {
        courseClient.findCourse(courseId).response!!
    } catch (e: FeignException) {
        logger.error("Error on finding course #$courseId, $e")
        throw CourseIntegrationException()
    }

    fun removeSubscription(userId: UUID) = try {
        courseClient.removeSubscription(userId)
    } catch (e: FeignException) {
        logger.error("Error on try remove subscription user #$userId, $e")
        throw CourseIntegrationException()
    }

}