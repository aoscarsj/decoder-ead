package authuser.core.user.exception

import authuser.common.rest.RestException
import authuser.common.rest.RestItemError
import org.springframework.http.HttpStatus

class UserRegistrationException(
    override val message: String,
    override val httpStatus: HttpStatus,
    override val errors: Collection<RestItemError> = emptyList()
) : RestException(message = message, httpStatus = httpStatus, errors = errors)