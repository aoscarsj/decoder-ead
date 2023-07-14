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
    override fun insert(user: User, subscriptionRequest: UserSubscriptionRequest): UserCourse {

        subscriptionRequest.apply {
            logger.info(
                """Starting validations for insert course #$courseId 
                enrollment in user #${user.userId}"""
            )
            if (userEnrolledInCourse(user, courseId))
                throw SubscriptionException("Error: subscription already exists!", CONFLICT)
            if (user.status == User.UserStatus.BLOCKED)
                throw SubscriptionException("Error: User is blocked!", UNAUTHORIZED)
            courseHelper.find(courseId)

            return userCourseRepository.save(UserCourse.from(user, subscriptionRequest.courseId))
        }

    }

}