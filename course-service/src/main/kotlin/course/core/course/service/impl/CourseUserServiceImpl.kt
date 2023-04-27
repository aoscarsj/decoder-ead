package course.core.course.service.impl

import course.core.course.data.Course
import course.core.course.data.CourseUser
import course.core.course.data.request.CourseSubscriptionRequest
import course.core.course.exception.CourseUserRegistrationException
import course.core.course.repository.CourseUserRepository
import course.core.course.service.CourseUserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.*

@Service
class CourseUserServiceImpl(
    private val courseUserRepository: CourseUserRepository
) : CourseUserService {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }
    override fun userEnrolledInCourse(course: Course, userId: UUID): Boolean {

        val userEnrolled = courseUserRepository.existsByCourseAndUserId(course, userId)
        if (userEnrolled) logger.warn("User #$userId enrolled with course #${course.courseId}")

        return userEnrolled
    }

    override fun insert(
        course: Course,
        subscriptionRequest: CourseSubscriptionRequest
    ): CourseUser {

        logger.info(
            """Starting validations for insert user #${subscriptionRequest.userId} 
                enrollment in course #${course.courseId}"""
        )
        if (userEnrolledInCourse(course, subscriptionRequest.userId))
            throw CourseUserRegistrationException(
                "Error: subscription already exists!", HttpStatus.CONFLICT
            )

        return courseUserRepository.save(CourseUser.from(course, subscriptionRequest.userId))
    }
}