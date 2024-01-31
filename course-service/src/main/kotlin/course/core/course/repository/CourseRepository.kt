package course.core.course.repository

import course.core.course.data.Course
import course.core.course.data.Course.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CourseRepository : JpaRepository<Course, UUID> {
    fun existsCourseByName(name: String): Boolean

    fun findAllByStatusAndLevel(status: CourseStatus, level: CourseLevel, pageable: Pageable):
            Page<Course>

    fun findAllByStatusAndNameContains(status: CourseStatus, name: String, pageable: Pageable):
            Page<Course>

    fun findAllByLevelAndNameContains(level: CourseLevel, name: String, pageable: Pageable):
            Page<Course>

    fun findAllByStatus(status: CourseStatus, pageable: Pageable): Page<Course>
    fun findAllByLevel(level: CourseLevel, pageable: Pageable): Page<Course>
    fun findAllByNameContains(name: String, pageable: Pageable): Page<Course>

    @Query(
        value = """SELECT CASE WHEN count(tcu) > 0 THEN true ELSE false END 
        FROM tb_courses_users tcu WHERE tcu.course_id = :courseId and tcu.user_id = :userId
    """, nativeQuery = true
    )
    fun existsByCourseAndUser(@Param("courseId") courseId:UUID, @Param("userId") userId:
    UUID): Boolean
    fun findAllByStatusAndLevelAndNameContains(
        status: CourseStatus,
        level: CourseLevel,
        name: String,
        pageable: Pageable
    ): Page<Course>

}