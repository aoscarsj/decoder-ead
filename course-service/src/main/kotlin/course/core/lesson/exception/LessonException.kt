package course.core.lesson.exception

import course.common.rest.RestException
import course.common.rest.RestItemError
import org.springframework.http.HttpStatus

class LessonException(
    override val message: String,
    override val httpStatus: HttpStatus,
    override val errors: Collection<RestItemError> = emptyList()
) : RestException(message = message, httpStatus = httpStatus)