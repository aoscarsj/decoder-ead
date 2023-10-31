package course.core.course.service.impl

import course.core.course.data.Course
import course.core.course.data.CourseUser
import course.core.course.data.request.CourseSubscriptionRequest
import course.core.course.exception.CourseUserRegistrationException
import course.core.course.helper.UserHelper
import course.core.course.repository.CourseUserRepository
import course.core.course.service.CourseUserService
import course.integrations.service.authuser.data.User
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CourseUserServiceImpl(
    private val courseUserRepository: CourseUserRepository,
    private val userHelper: UserHelper
) : CourseUserService {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }
    override fun userEnrolledInCourse(course: Course, userId: UUID): Boolean {

        val userEnrolled = courseUserRepository.existsByCourseAndUserId(course, userId)
        if (userEnrolled) logger.warn("User #$userId enrolled with course #${course.courseId}")

        return userEnrolled
    }

    override fun findAllByUser(userId: UUID, pageable: Pageable): Page<Course> {
        logger.info("Searching courses by user #$userId")
        val courses = courseUserRepository.findAllByUserId(userId, pageable).map { it.course }
        logger.info("courses found: $courses, size: ${courses.totalElements}")
        return courses
    }

    @Transactional
    override fun removeByCourse(course: Course) {
        logger.info("Removing courseUser by course #${course.courseId}")
        val courseUsers = courseUserRepository.findAllByCourse(course)
        logger.info(
            "courses users found: $courseUsers, size: ${courseUsers.size}, starting " +
                    "removal"
        )
        courseUserRepository.deleteAll(courseUsers)
    }

    @Transactional
    override fun removeByUser(userId: UUID) {
        logger.info("Starting removal userCourses based on userId #$userId")
        courseUserRepository.removeAllByUserId(userId)
    }

    @Transactional
    override fun insert(
        course: Course,
        subscriptionRequest: CourseSubscriptionRequest,
        userNotify: Boolean
    ): CourseUser {

        logger.info(
            """Starting validations for insert user #${subscriptionRequest.userId} 
                enrollment in course #${course.courseId}"""
        )
        if (userEnrolledInCourse(course, subscriptionRequest.userId))
            throw CourseUserRegistrationException(
                "Error: subscription already exists!", HttpStatus.CONFLICT
            )
        val user = userHelper.findUser(subscriptionRequest.userId)
        if (user.status == User.UserStatus.BLOCKED)
            throw CourseUserRegistrationException(
                "Error: User is blocked.", HttpStatus.UNAUTHORIZED
            )
        if (userNotify)
            notifyAuthuserSubscription(subscriptionRequest.userId, course.courseId!!)
        val savedCourseUser = courseUserRepository.save(
            CourseUser.from(course, subscriptionRequest.userId)
        )
        logger.info("User ${user.userId} successfully registered in course ${course.courseId}")
        return savedCourseUser
    }

    private fun notifyAuthuserSubscription(userId: UUID, courseId: UUID) {
        logger.info("Sending subscription notify to authuser, userId $userId, courseId $courseId")
        userHelper.sendSubscription(userId, courseId)
    }
}