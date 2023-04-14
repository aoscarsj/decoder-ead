package authuser.core.user.repository

import authuser.core.user.data.UserCourse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserCourseRepository: JpaRepository<UserCourse, UUID> {
    fun findAllByCourseId(courseId: UUID, pageable: Pageable): Page<UserCourse>
}