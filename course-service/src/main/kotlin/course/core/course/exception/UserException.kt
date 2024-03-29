package course.core.course.exception

import course.common.rest.RestException
import course.common.rest.RestItemError
import org.springframework.http.HttpStatus

class UserException(
    override val message: String,
    override val httpStatus: HttpStatus = HttpStatus.NOT_FOUND,
    override val errors: Collection<RestItemError> = emptyList()
) : RestException(message = message, httpStatus = httpStatus)