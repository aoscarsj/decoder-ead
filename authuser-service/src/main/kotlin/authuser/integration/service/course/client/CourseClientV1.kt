package authuser.integration.service.course.client

import authuser.common.const.COURSE_SERVICES_COURSES_BY_USERS_RESOURCE
import authuser.common.rest.RestResponse
import authuser.integration.service.course.data.Course
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.util.*


@FeignClient(name = "course-services-v1", url = "\${ead.api.url.course}")
interface CourseClientV1 {

    @GetMapping(COURSE_SERVICES_COURSES_BY_USERS_RESOURCE)
    fun findAllCoursesByUser(@PathVariable userId: UUID, page: Pageable): RestResponse<Page<Course>>

}
