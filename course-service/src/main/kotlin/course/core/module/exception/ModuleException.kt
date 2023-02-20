package course.core.module.exception

import course.common.rest.RestException
import course.common.rest.RestItemError
import org.springframework.http.HttpStatus

class ModuleException(
    override val message: String,
    override val httpStatus: HttpStatus,
    override val errors: Collection<RestItemError> = emptyList()
) : RestException(message = message, httpStatus = httpStatus)