package authuser.core.user.service.impl

import authuser.core.user.repository.UserCourseRepository
import authuser.core.user.service.UserCourseService
import org.springframework.stereotype.Service

@Service
class UserCourseServiceImpl(
    private val userCourseRepository: UserCourseRepository
) : UserCourseService {

}