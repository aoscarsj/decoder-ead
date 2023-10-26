package authuser.core.user.exception

import authuser.common.rest.RestException
import authuser.common.rest.RestItemError
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR

class CourseIntegrationException(
    override val message: String = INTERNAL_SERVER_ERROR.toString(),
    override val httpStatus: HttpStatus = INTERNAL_SERVER_ERROR,
    override val errors: Collection<RestItemError> = emptyList()
) : RestException(httpStatus, message = message)