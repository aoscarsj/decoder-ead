package course.core.module.rest.v1

import course.common.rest.RestResponse
import course.core.course.service.CourseService
import course.core.module.data.Module
import course.core.module.data.request.ModuleRegistrationRequest
import course.core.module.data.request.ModuleUpdateRequest
import course.core.module.service.ModuleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class ModuleRestV1(
    private val moduleService: ModuleService,
    private val courseService: CourseService
) {

    @PostMapping("/courses/{courseId}/modules")
    fun insert(
        @PathVariable(value = "courseId") courseId: UUID,
        @RequestBody @Valid moduleRegistrationRequest: ModuleRegistrationRequest,
    ): RestResponse<Module> {

        val course = courseService.find(courseId)
        val module = moduleService.create(course, moduleRegistrationRequest)

        return RestResponse(
            "Module was successfully created", response = module, httpStatus =
            HttpStatus.CREATED
        )
    }

    @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
    fun remove(
        @PathVariable(value = "courseId") courseId: UUID,
        @PathVariable(value = "moduleId") moduleId: UUID,
    ): RestResponse<Nothing> {

        moduleService.delete(courseId = courseId, moduleId = moduleId)
        return RestResponse("Module deleted successful")
    }

    @PutMapping("/courses/{courseId}/modules/{moduleId}")
    fun update(
        @PathVariable(value = "courseId") courseId: UUID,
        @PathVariable(value = "moduleId") moduleId: UUID,
        @RequestBody @Valid updateRequest: ModuleUpdateRequest
    ): RestResponse<Module> {

        val module = moduleService.update(courseId, moduleId, updateRequest)
        return RestResponse("Module was successfully updated", module)
    }

    @GetMapping("/courses/{courseId}/modules")
    fun findAll(
        @PathVariable(value = "courseId") courseId: UUID
    ): RestResponse<List<Module>> =
        RestResponse("Module was collected", moduleService.findAllIntoCourse(courseId))

    @GetMapping("/courses/{courseId}/modules/{moduleId}")
    fun find(
        @PathVariable(value = "courseId") courseId: UUID,
        @PathVariable(value = "moduleId") moduleId: UUID
    ): RestResponse<Module> =
        RestResponse("Module was collected", moduleService.findIntoCourse(courseId, moduleId))

}