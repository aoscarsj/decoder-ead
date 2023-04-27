package course.core.course.service

import course.core.course.data.Course
import course.core.course.data.CourseUser
import course.core.course.data.request.CourseSubscriptionRequest
import java.util.*

interface CourseUserService {
    fun userEnrolledInCourse(course: Course, userId: UUID): Boolean
    fun insert(course: Course, subscriptionRequest: CourseSubscriptionRequest): CourseUser
}