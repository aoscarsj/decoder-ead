package course.core.course.service

import course.core.course.data.User
import java.util.UUID

interface UserService {
    fun save(user: User): User
    fun delete(userId: UUID)
}