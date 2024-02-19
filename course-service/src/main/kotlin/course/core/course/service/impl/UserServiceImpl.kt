package course.core.course.service.impl

import course.core.course.data.User
import course.core.course.exception.UserException
import course.core.course.repository.CourseRepository
import course.core.course.repository.UserRepository
import course.core.course.service.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository
) : UserService {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }
    override fun save(user: User): User {
        logger.info("Saving user $user")
        return userRepository.save(user)
    }

    @Transactional
    override fun delete(userId: UUID) {
        logger.info("Deleting user #$userId")
        courseRepository.deleteCourseUserByUser(userId)
        userRepository.deleteById(userId)
    }

    override fun find(userId: UUID): User {
        logger.info("Searching user #$userId")

        return userRepository.findByUserId(userId) ?: throw UserException("User $userId not found")
    }

    override fun findAllUsersInCourse(courseId: UUID, pageable: Pageable): Page<User> {
        logger.info("Searching users by course #$courseId")

        return userRepository.findAllUsersByCourse(courseId, pageable)
    }

}