package course.integrations.service.authuser.client

import course.common.const.*
import course.common.rest.RestException
import course.common.rest.RestResponse
import course.integrations.service.authuser.data.User
import course.integrations.service.authuser.data.UserCourse
import course.integrations.service.authuser.data.UserSubscriptionRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import java.util.*

@FeignClient(name = AUTHUSER_SERVICES)
interface AuthUserClient {

    @GetMapping(AUTHUSER_SERVICES_USERS_BY_COURSE_RESOURCE)
    fun findAllUsersByCourse(@PathVariable courseId: UUID, page: Pageable): RestResponse<Page<User>>

    @Throws(RestException::class)
    @GetMapping(AUTHUSER_SERVICES_USER_BASE_RESOURCE)
    fun findUser(@PathVariable userId: UUID): RestResponse<User>

    @DeleteMapping(AUTHUSER_SERVICES_INTERNAL_USERS_BY_COURSE_RESOURCE)
    fun removeSubscription(@PathVariable courseId: UUID): RestResponse<Any>

    @Throws(RestException::class)
    @PostMapping(AUTHUSER_SERVICES_USER_SUBSCRIPTION_RESOURCE)
    fun sendSubscription(
        @PathVariable userId: UUID,
        @RequestBody subscriptionRequest: UserSubscriptionRequest
    ): RestResponse<UserCourse>
}