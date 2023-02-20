package course.core.lesson.data.request

data class LessonUpdateRequest(
    val title: String? = null,
    val description: String? = null,
    val videoUrl: String? = null
)
