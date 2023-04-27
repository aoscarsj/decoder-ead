package course.core.course.data.request

import java.util.UUID
import javax.validation.constraints.NotNull

data class CourseSubscriptionRequest (
    @NotNull
    val userId: UUID
)