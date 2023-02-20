package authuser.common.rest

import org.springframework.http.HttpStatus

open class RestException(
    open val httpStatus: HttpStatus,
    val httpCode: Int = httpStatus.value(),
    override val message: String,
    open val errors: Collection<RestItemError> = emptyList()
) : Exception(message)