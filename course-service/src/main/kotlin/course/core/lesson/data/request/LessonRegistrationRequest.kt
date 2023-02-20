package course.core.lesson.data.request

import javax.validation.constraints.NotBlank

data class LessonRegistrationRequest(
    @NotBlank
    val title: String,
    val description: String,
    @NotBlank
    val videoUrl: String
)