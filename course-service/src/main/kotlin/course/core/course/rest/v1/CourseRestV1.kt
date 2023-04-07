package course.core.course.rest.v1

import course.common.rest.RestResponse
import course.core.course.data.Course
import course.core.course.data.request.CourseRegistrationRequest
import course.core.course.data.request.CourseSearchRequest
import course.core.course.data.request.CourseUpdateRequest
import course.core.course.service.CourseService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = ["*"], maxAge = 3600)
class CourseRestV1(
    private val courseService: CourseService
) {
    @PostMapping
    fun insert(@RequestBody @Valid courseRegistrationRequest: CourseRegistrationRequest):
            RestResponse<Course> {

        val course = courseService.create(courseRegistrationRequest)
        return RestResponse(
            message = "Course was successfully created", response = course,
            httpStatus = HttpStatus.CREATED
        )
    }

    @DeleteMapping("/{courseId}")
    fun remove(@PathVariable(value = "courseId") courseId: UUID): RestResponse<Any> {

        courseService.delete(courseId)
        return RestResponse("Course deleted successful")
    }

    @PutMapping("/{courseId}")
    fun update(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody @Valid courseUpdateRequest: CourseUpdateRequest
    ): RestResponse<Course> {

        val course = courseService.update(courseId, courseUpdateRequest)
        return RestResponse("Course was successfully updated", course)
    }

    @GetMapping
    fun findAll(
        searchRequest: CourseSearchRequest,
        @PageableDefault(
            page = 0, size = 10, sort = ["courseId"], direction = Sort.Direction.ASC
        ) page: Pageable
    ): RestResponse<Page<Course>> =
        RestResponse("Courses was collected", courseService.findAll(searchRequest, page))

    @GetMapping("/users/{userId}")
    fun findAllByUser(
        @PathVariable userId: UUID, @PageableDefault(
            page = 0, size = 10, sort = ["userId"], direction = Sort.Direction.ASC
        ) page: Pageable
    ): RestResponse<Page<Course>> =
        RestResponse("Courses was collected", courseService.findAllByUser(userId, page))


    @GetMapping("/{courseId}")
    fun find(@PathVariable(value = "courseId") courseId: UUID): RestResponse<Course> =
        RestResponse("Course was collected", courseService.find(courseId))
}