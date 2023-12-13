package course.core.course.service

import course.core.course.data.User

interface UserService {
    fun save(user: User): User
}