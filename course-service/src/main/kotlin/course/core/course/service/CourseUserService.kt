package course.core.course.service

import course.core.course.data.Course
import course.core.course.data.CourseUser
import course.core.course.data.request.CourseSubscriptionRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface CourseUserService {
    fun userEnrolledInCourse(course: Course, userId: UUID): Boolean
    fun findAllByUser(userId: UUID, pageable: Pageable): Page<Course>
    fun removeByCourse(course: Course)
    fun removeByUser(userId: UUID)
    fun insert(
        course: Course, subscriptionRequest: CourseSubscriptionRequest, userNotify:
        Boolean = true
    ): CourseUser
}