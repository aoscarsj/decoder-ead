package course.core.lesson.service

import course.core.lesson.data.Lesson
import course.core.lesson.data.request.LessonRegistrationRequest
import course.core.lesson.data.request.LessonUpdateRequest
import course.core.module.data.Module
import java.util.*

interface LessonService {
    fun removeAllIntoModule(moduleId: UUID)
    fun create(module: Module, lessonRegistrationRequest: LessonRegistrationRequest): Lesson
    fun delete(moduleId: UUID, lessonId: UUID)

    fun update(moduleId: UUID, lessonId: UUID, updateRequest: LessonUpdateRequest): Lesson
    fun findIntoModule(moduleId: UUID, lessonId: UUID): Lesson
    fun findAllIntoModule(moduleId: UUID): List<Lesson>
}