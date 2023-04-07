package course.core.course.data.request

import course.core.course.data.Course.CourseLevel
import course.core.course.data.Course.CourseStatus

data class CourseSearchRequest (
    val courseLevel: CourseLevel? = null,
    val courseStatus: CourseStatus? = null,
    var name: String? = null
)