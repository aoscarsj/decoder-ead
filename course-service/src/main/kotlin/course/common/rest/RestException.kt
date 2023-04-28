package course.common.rest

import org.springframework.http.HttpStatus

open class RestException(
    open val httpStatus: HttpStatus,
    override val message: String,
    val httpCode: Int = httpStatus.value(),
    open val errors: Collection<RestItemError> = emptyList()
) : Exception(message)