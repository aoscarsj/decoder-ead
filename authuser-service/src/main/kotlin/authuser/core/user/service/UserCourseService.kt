package authuser.core.user.service

import authuser.core.user.data.User
import authuser.core.user.data.UserCourse
import authuser.core.user.data.request.UserSubscriptionRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserCourseService {
    fun userEnrolledInCourse(user: User, courseId: UUID): Boolean
    fun insert(
        user: User,
        subscriptionRequest: UserSubscriptionRequest,
        courseNotify: Boolean = true
    ): UserCourse

    fun findAllByCourse(courseId: UUID, pageable: Pageable): Page<UserCourse>
    fun removeAllByUser(userId: UUID)
}