package course.core.course.data.request

import course.core.course.data.CourseLevel
import course.core.course.data.CourseStatus

data class CourseSearchRequest (
    val courseLevel: CourseLevel? = null,
    val courseStatus: CourseStatus? = null,
    var name: String? = null
)