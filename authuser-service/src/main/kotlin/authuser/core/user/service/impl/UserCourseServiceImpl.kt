package authuser.core.user.service.impl

import authuser.core.user.data.User
import authuser.core.user.data.UserCourse
import authuser.core.user.data.request.UserSubscriptionRequest
import authuser.core.user.exception.SubscriptionException
import authuser.core.user.helper.CourseHelper
import authuser.core.user.repository.UserCourseRepository
import authuser.core.user.service.UserCourseService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus.CONFLICT
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserCourseServiceImpl(
    private val userCourseRepository: UserCourseRepository,
    private val courseHelper: CourseHelper
) : UserCourseService {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    override fun userEnrolledInCourse(user: User, courseId: UUID): Boolean {

        val userEnrolled = userCourseRepository.existsByUserAndCourseId(user, courseId)
        if (userEnrolled) logger.warn("User #${user.userId} enrolled with course #$courseId")

        return userEnrolled
    }

    @Transactional
    override fun insert(
        user: User,
        subscriptionRequest: UserSubscriptionRequest,
        courseNotify: Boolean
    ):
            UserCourse {

        subscriptionRequest.apply {
            logger.info(
                """Starting validations for insert course #$courseId 
                enrollment in user #${user.userId}"""
            )
            if (userEnrolledInCourse(user, courseId))
                throw SubscriptionException("Error: subscription already exists!", CONFLICT)
            if (user.status == User.UserStatus.BLOCKED)
                throw SubscriptionException("Error: User is blocked!", UNAUTHORIZED)

            if (courseNotify) notifyCourse(courseId, user.userId!!)

            return userCourseRepository.save(UserCourse.from(user, subscriptionRequest.courseId))
        }

    }

    private fun notifyCourse(courseId: UUID, userId: UUID) {
        courseHelper.sendSubscription(courseId, userId)
    }

    override fun findAllByCourse(courseId: UUID, pageable: Pageable): Page<UserCourse> {
        return userCourseRepository.findAllByCourseId(courseId, pageable)
    }

    @Transactional
    override fun removeAllByUser(userId: UUID) {
        logger.warn("Starting deletion by userId #$userId")

        val userCourses = userCourseRepository.findAllByUserUserId(userId)
        logger.warn("Deleting $userCourses")
        courseHelper.removeSubscription(userId)
        userCourseRepository.deleteAll(userCourses)
    }

    @Transactional
    override fun removeAllByCourse(courseId: UUID) {
        logger.warn("Starting deletion userCourse by course #$courseId")

        userCourseRepository.removeAllByCourseId(courseId)
    }

}