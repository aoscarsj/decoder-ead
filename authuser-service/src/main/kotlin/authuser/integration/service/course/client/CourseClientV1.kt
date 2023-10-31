package authuser.integration.service.course.client

import authuser.common.const.COURSE_SERVICES
import authuser.common.const.COURSE_SERVICES_COURSES_BY_USERS_RESOURCE
import authuser.common.const.COURSE_SERVICES_COURSE_FIND_RESOURCE
import authuser.common.const.COURSE_SERVICES_SUBSCRIPTION_RESOURCE
import authuser.common.rest.RestResponse
import authuser.integration.service.course.data.Course
import authuser.integration.service.course.data.request.SubscriptionRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.util.*


@FeignClient(name = COURSE_SERVICES)
interface CourseClientV1 {

    @GetMapping(COURSE_SERVICES_COURSES_BY_USERS_RESOURCE)
    fun findAllCoursesByUser(@PathVariable userId: UUID, page: Pageable): RestResponse<Page<Course>>

    @PostMapping(COURSE_SERVICES_SUBSCRIPTION_RESOURCE)
    fun saveSubscriptionUserInCourse(
        @PathVariable courseId: UUID, subscription: SubscriptionRequest
    ): RestResponse<Any>

    @GetMapping(COURSE_SERVICES_COURSE_FIND_RESOURCE)
    fun findCourse(@PathVariable courseId: UUID): RestResponse<Course>

    @DeleteMapping(COURSE_SERVICES_COURSES_BY_USERS_RESOURCE)
    fun removeSubscription(@PathVariable userId: UUID)
}
