package course.core.course.repository

import course.core.course.data.Course
import course.core.course.data.CourseLevel
import course.core.course.data.CourseStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
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
    fun findAllByStatusAndLevelAndNameContains(
        status: CourseStatus,
        level: CourseLevel,
        name: String,
        pageable: Pageable
    ): Page<Course>

}