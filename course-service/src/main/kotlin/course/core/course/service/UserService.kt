package course.core.course.service

import course.core.course.data.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface UserService {
    fun save(user: User): User
    fun delete(userId: UUID)
    fun find(userId: UUID): User
    fun findAllUsersInCourse(courseId: UUID, pageable: Pageable): Page<User>
}