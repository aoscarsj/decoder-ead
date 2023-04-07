package authuser.integration.service.course.data

import java.util.UUID

data class Course(
    val courseId: UUID?,
    val name: String,
    val description: String,
    val imageUrl: String,
    val status: CourseStatus,
    val instructorId: UUID,
    val level: CourseLevel
){

    enum class CourseStatus { IN_PROGRESS, CONCLUDED }
    enum class CourseLevel { BEGINNER, INTERMEDIARY, ADVANCED }
}

