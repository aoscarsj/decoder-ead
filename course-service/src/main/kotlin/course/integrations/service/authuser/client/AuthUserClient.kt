package course.integrations.service.authuser.client

import course.common.const.AUTHUSER_SERVICES_USERS_BY_COURSE_RESOURCE
import course.common.const.AUTHUSER_SERVICES_USER_BASE_RESOURCE
import course.common.rest.RestException
import course.common.rest.RestResponse
import course.integrations.service.authuser.data.User
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*
import kotlin.jvm.Throws

@FeignClient(name = "authuser-services-v1", url = "\${ead.api.url.authuser}")
interface AuthUserClient {

    @GetMapping(AUTHUSER_SERVICES_USERS_BY_COURSE_RESOURCE)
    fun findAllUsersByCourse(@PathVariable courseId: UUID, page: Pageable): RestResponse<Page<User>>

    @Throws(RestException::class)
    @GetMapping(AUTHUSER_SERVICES_USER_BASE_RESOURCE)
    fun findUser(@PathVariable userId: UUID): RestResponse<User>
}