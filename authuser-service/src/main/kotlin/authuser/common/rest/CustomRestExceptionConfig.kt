package authuser.common.rest

import org.springframework.core.env.Environment
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomRestExceptionConfig(
    private val environment: Environment
) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    fun exceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        return if (ex is RestException) buildRestException(ex) else buildInternalServerError(ex)
    }

    fun buildInternalServerError(ex: Exception): ResponseEntity<Any> {

        val restResponse = RestResponse<Any>(
            success = false,
            message = "Internal Server Error",
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
            httpCode = 500
        )

        logger.error(ex.message, ex)

        return ResponseEntity(restResponse, HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR)

    }

    private fun buildRestException(ex: Exception): ResponseEntity<Any> {

        val restException = ex as RestException

        val mappedErrors = mutableListOf<RestItemError>()

        val applicationName = environment.getProperty("spring.application.name")
        val applicationPrefix = environment.getProperty("application.prefix")

        restException.errors.forEach {
            val error = environment.getProperty("code.${applicationName}.${it.code}")

            if (error != null && error.isNotEmpty())
                mappedErrors.add(RestItemError("${applicationPrefix}:${it.code}", error))
            else
                mappedErrors.add(RestItemError("${applicationPrefix}:${it.code}", it.error))
        }

        val restResponse = RestResponse<Any>(
            success = false,
            message = restException.message,
            httpStatus = restException.httpStatus,
            httpCode = restException.httpCode,
            errors = mappedErrors
        )

        val httpStatus: HttpStatus = getStatus(restResponse) ?: HttpStatus.INTERNAL_SERVER_ERROR

        return ResponseEntity(restResponse, HttpHeaders(), httpStatus)

    }

    private fun getStatus(restResponse: RestResponse<Any>): HttpStatus? {
        return HttpStatus.resolve(restResponse.httpCode)
    }
}