package authuser.common.rest

import org.springframework.http.HttpStatus

open class RestResponse<T>(
    val message: String,
    val response: T? = null,
    val success: Boolean = true,
    val httpStatus: HttpStatus = HttpStatus.OK,
    val httpCode: Int = httpStatus.value(),
    val errors: Collection<RestItemError> = emptyList()
)
