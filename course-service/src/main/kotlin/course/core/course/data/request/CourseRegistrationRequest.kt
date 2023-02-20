package course.core.course.data.request

import course.core.course.data.CourseLevel
import course.core.course.data.CourseStatus
import java.util.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseRegistrationRequest(
    @NotBlank
    val name: String,
    @NotBlank
    val description: String,
    val imageUrl: String,
    @NotNull
    val status: CourseStatus,
    @NotNull
    val instructorId: UUID,
    @NotNull
    val level: CourseLevel
)