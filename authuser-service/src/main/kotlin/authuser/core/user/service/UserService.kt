package authuser.core.user.service

import authuser.core.user.data.request.UserCreateRequest
import authuser.core.user.data.request.UserSearchRequest
import authuser.core.user.data.request.UserUpdateRequest
import authuser.core.user.data.User
import authuser.core.user.data.request.InstructorRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {

    fun findAll(searchRequest: UserSearchRequest, page: Pageable): Page<User>
    fun find(userId: UUID): User
    fun delete(userId: UUID)
    fun signup(request: UserCreateRequest): User
    fun existsByUsername(user: User): Boolean
    fun update(userId: UUID, updateRequest: UserUpdateRequest): User
    fun updatePassword(userId: UUID, updateRequest: UserUpdateRequest)
    fun updateImage(userId: UUID, updateRequest: UserUpdateRequest): User
    fun insertInstructor(instructorRequest: InstructorRequest): User
    fun save(user: User): User
    fun saveAndPublish(user: User): User
}