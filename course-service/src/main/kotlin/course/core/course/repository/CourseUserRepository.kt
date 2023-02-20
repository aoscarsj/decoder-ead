package course.core.course.repository

import course.core.course.data.CourseUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CourseUserRepository : JpaRepository<CourseUser, UUID>