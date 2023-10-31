package course.core.course.repository

import course.core.course.data.Course
import course.core.course.data.CourseUser
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourseUserRepository : JpaRepository<CourseUser, UUID> {

    fun findAllByUserId(userId: UUID, pageable: Pageable): Page<CourseUser>
    fun removeAllByUserId(userId: UUID)
    fun findAllByCourse(course: Course): List<CourseUser>
    fun existsByCourseAndUserId(course: Course, userId: UUID): Boolean
}