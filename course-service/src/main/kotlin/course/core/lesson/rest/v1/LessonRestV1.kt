package course.core.lesson.rest.v1

import course.common.rest.RestResponse
import course.core.lesson.data.Lesson
import course.core.lesson.data.request.LessonRegistrationRequest
import course.core.lesson.data.request.LessonUpdateRequest
import course.core.lesson.service.LessonService
import course.core.module.service.ModuleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
class LessonRestV1(
    private val lessonService: LessonService,
    private val moduleService: ModuleService
) {
    @PostMapping("/modules/{moduleId}/lessons")
    fun insert(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @RequestBody @Valid lessonRegistrationRequest: LessonRegistrationRequest,
    ): RestResponse<Lesson> {

        val module = moduleService.find(moduleId)
        val lesson = lessonService.create(module, lessonRegistrationRequest)
        return RestResponse(
            "Lesson was successfully created", response = lesson, httpStatus =
            HttpStatus.CREATED
        )
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun remove(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @PathVariable(value = "lessonId") lessonId: UUID,
    ): RestResponse<Nothing> {

        lessonService.delete(moduleId = moduleId, lessonId = lessonId)
        return RestResponse("Lesson deleted successful")
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun update(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @PathVariable(value = "lessonId") lessonId: UUID,
        @RequestBody @Valid updateRequest: LessonUpdateRequest
    ): RestResponse<Lesson> {

        val lesson = lessonService.update(moduleId, lessonId, updateRequest)
        return RestResponse("Lesson was successfully updated", lesson)
    }

    @GetMapping("/modules/{moduleId}/lessons")
    fun findAll(
        @PathVariable(value = "moduleId") moduleId: UUID
    ): RestResponse<List<Lesson>> =
        RestResponse("Lesson was collected", lessonService.findAllIntoModule(moduleId))

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    fun find(
        @PathVariable(value = "moduleId") moduleId: UUID,
        @PathVariable(value = "lessonId") lessonId: UUID,
    ): RestResponse<Lesson> =
        RestResponse("Lesson was collected", lessonService.findIntoModule(moduleId, lessonId))

}