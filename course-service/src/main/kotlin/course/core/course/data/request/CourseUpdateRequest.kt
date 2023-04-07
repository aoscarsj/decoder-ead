package course.core.course.data.request

import course.core.course.data.Course.*
import java.util.*

data class CourseUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val status: CourseStatus? = null,
    val instructorId: UUID? = null,
    val level: CourseLevel? = null
)