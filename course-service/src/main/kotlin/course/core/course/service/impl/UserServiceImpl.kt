package course.core.course.service.impl

import course.core.course.data.User
import course.core.course.repository.UserRepository
import course.core.course.service.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
) : UserService {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }
    override fun save(user: User): User {
        logger.info("Saving user $user")
        return userRepository.save(user)
    }

    override fun delete(userId: UUID) {
        logger.info("Deleting user #$userId")
        userRepository.deleteById(userId)
    }

}