package authuser.core.user.service

import authuser.core.user.data.User
import authuser.core.user.data.UserCourse
import authuser.core.user.data.request.UserSubscriptionRequest
import java.util.*

interface UserCourseService {
    fun userEnrolledInCourse(user: User, courseId: UUID): Boolean
    fun insert(user: User, subscriptionRequest: UserSubscriptionRequest): UserCourse
}