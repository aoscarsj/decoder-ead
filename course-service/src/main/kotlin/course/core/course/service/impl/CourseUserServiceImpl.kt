package course.core.course.service.impl

import course.core.course.repository.CourseUserRepository
import course.core.course.service.CourseUserService
import org.springframework.stereotype.Service

@Service
class CourseUserServiceImpl(
    private val courseUserRepository: CourseUserRepository
) : CourseUserService {
}