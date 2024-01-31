package course.core.course.repository

import course.core.course.data.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByUserId(userId: UUID): User?
}