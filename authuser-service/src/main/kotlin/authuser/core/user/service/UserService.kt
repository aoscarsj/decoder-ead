package authuser.core.user.service

import authuser.core.user.data.UserCreateRequest
import authuser.core.user.data.UserSearchRequest
import authuser.core.user.data.UserUpdateRequest
import authuser.core.user.data.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserService {

    fun findAll(searchRequest: UserSearchRequest, page: Pageable): Page<User>
    fun findById(userId: UUID): User?
    fun delete(userId: UUID)
    fun signup(request: UserCreateRequest): User
    fun existsByUsername(user: User): Boolean
    fun update(userId: UUID, updateRequest: UserUpdateRequest): User
    fun updatePassword(userId: UUID, updateRequest: UserUpdateRequest)
    fun updateImage(userId: UUID, updateRequest: UserUpdateRequest): User
}