package course.core.course.repository

import course.core.course.data.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {
    fun findByUserId(userId: UUID): User?


    @Query(
        value = """
        select * from tb_users INNER JOIN tb_courses_users tcu on tb_users.user_id = tcu.user_id
            where course_id = :courseId
        """, nativeQuery = true
    )
    fun findAllUsersByCourse(@Param("courseId") courseId: UUID, pageable: Pageable): Page<User>
}