package authuser.core.user.repository

import authuser.core.user.data.User
import authuser.core.user.data.User.UserStatus
import authuser.core.user.data.User.UserType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun existsByUsername(username: String): Boolean
    fun existsByEmail(email: String): Boolean
    fun existsByCpf(cpf: String): Boolean
    fun findAllByTypeAndStatusAndEmailContains(
        type: UserType, status: UserStatus, email: String,
        pageable: Pageable
    )
            : Page<User>

    fun findAllByTypeAndStatus(type: UserType, status: UserStatus, pageable: Pageable): Page<User>
    fun findAllByTypeAndEmailContains(type: UserType, email: String, pageable: Pageable): Page<User>
    fun findAllByStatusAndEmailContains(status: UserStatus, email: String, pageable: Pageable):
            Page<User>

    fun findAllByType(type: UserType, pageable: Pageable): Page<User>
    fun findAllByStatus(status: UserStatus, pageable: Pageable): Page<User>
    fun findAllByEmailContains(email: String, pageable: Pageable): Page<User>

    @Query(
        value =
        """
            SELECT *
            FROM tb_users us
                INNER JOIN tb_users_courses tuc on tuc.course_id = :courseId 
                WHERE us.user_id = tuc.user_user_id
        """, nativeQuery = true
    )
    fun findAllOfCourse(courseId: UUID): List<User>
}