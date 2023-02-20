package course.core.module.service

import course.core.course.data.Course
import course.core.module.data.Module
import course.core.module.data.request.ModuleRegistrationRequest
import course.core.module.data.request.ModuleUpdateRequest
import java.util.*

interface ModuleService {
    fun delete(courseId: UUID, moduleId: UUID)
    fun update(courseId: UUID, moduleId: UUID, updateRequest: ModuleUpdateRequest): Module
    fun find(moduleId: UUID): Module
    fun findIntoCourse(courseId: UUID, moduleId: UUID): Module
    fun findAllIntoCourse(courseId: UUID): List<Module>
    fun removeAllIntoCourse(courseId: UUID)
    fun create(course: Course, moduleRegistrationRequest: ModuleRegistrationRequest): Module
}