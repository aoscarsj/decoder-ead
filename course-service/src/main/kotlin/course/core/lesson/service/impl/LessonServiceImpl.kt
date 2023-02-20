package course.core.lesson.service.impl

import course.core.lesson.data.Lesson
import course.core.lesson.data.request.LessonRegistrationRequest
import course.core.lesson.data.request.LessonUpdateRequest
import course.core.lesson.exception.LessonException
import course.core.lesson.repository.LessonRepository
import course.core.lesson.service.LessonService
import course.core.module.data.Module
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class LessonServiceImpl(
    private val lessonRepository: LessonRepository,
) : LessonService {
    @Transactional
    override fun removeAllIntoModule(moduleId: UUID) {

        val lessons = findAllIntoModule(moduleId)

        if (lessons.isNotEmpty())
            lessonRepository.deleteAll(lessons)
    }

    override fun create(
        module: Module,
        lessonRegistrationRequest: LessonRegistrationRequest
    ): Lesson =
        lessonRepository.save(Lesson.from(lessonRegistrationRequest, module))

    @Transactional
    override fun delete(moduleId: UUID, lessonId: UUID) {

        val lesson = findIntoModule(moduleId, lessonId)
        lessonRepository.delete(lesson)
    }


    override fun update(
        moduleId: UUID,
        lessonId: UUID,
        updateRequest: LessonUpdateRequest
    ): Lesson {

        val lesson = findIntoModule(moduleId, lessonId)
        updateRequest.apply {
            description?.let { lesson.description = description }
            title?.let { lesson.title = title }
            videoUrl?.let { lesson.videoUrl = videoUrl }
        }

        return lessonRepository.save(lesson)
    }

    override fun findIntoModule(moduleId: UUID, lessonId: UUID): Lesson =
        lessonRepository.findIntoModule(moduleId, lessonId)
            ?: throw LessonException("Lesson not found for this module", HttpStatus.NOT_FOUND)

    override fun findAllIntoModule(moduleId: UUID): List<Lesson> =
        lessonRepository.findAllIntoModule(moduleId)


}