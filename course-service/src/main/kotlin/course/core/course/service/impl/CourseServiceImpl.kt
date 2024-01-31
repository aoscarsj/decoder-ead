package course.core.course.service.impl

import course.core.course.data.Course
import course.core.course.data.User
import course.core.course.data.request.CourseRegistrationRequest
import course.core.course.data.request.CourseSearchRequest
import course.core.course.data.request.CourseSubscriptionRequest
import course.core.course.data.request.CourseUpdateRequest
import course.core.course.exception.CourseException
import course.core.course.exception.CourseRegistrationException
import course.core.course.exception.CourseUserSubscriptionException
import course.core.course.helper.UserHelper
import course.core.course.repository.CourseRepository
import course.core.course.service.CourseService
import course.core.course.service.UserService
import course.core.module.service.ModuleService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class CourseServiceImpl(
    private val courseRepository: CourseRepository,
    private val moduleService: ModuleService,
    private val userHelper: UserHelper,
    private val userService: UserService
) : CourseService {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    override fun delete(courseId: UUID) {

        val course = find(courseId)
        moduleService.removeAllIntoCourse(courseId)
        userHelper.removeSubscription(courseId)
        courseRepository.delete(course)
    }

    override fun find(courseId: UUID): Course =
        courseRepository.findByIdOrNull(courseId) ?: throw CourseException(
            "Course not found", NOT_FOUND
        )

    override fun create(registrationRequest: CourseRegistrationRequest): Course {

        validateCreateRequest(registrationRequest)
        val course = Course.from(registrationRequest)

        if (courseRepository.existsCourseByName(course.name)) throw CourseRegistrationException(
            "Error: name is Already taken!",
            CONFLICT
        )

        return courseRepository.save(course)
    }

    override fun update(courseId: UUID, updateRequest: CourseUpdateRequest): Course {

        validateUpdateRequest(updateRequest)
        val course = find(courseId)

        updateRequest.apply {

            if (name != null) course.name = name
            if (description != null) course.description = description
            if (imageUrl != null) course.imageUrl = imageUrl
            if (status != null) course.status = status
            if (instructorId != null) course.instructorId = instructorId
            if (level != null) course.level = level
        }

        return courseRepository.save(course)
    }

    override fun findAll(): List<Course> = courseRepository.findAll()

    fun Any?.isNonNull() = this != null
    override fun findAll(searchRequest: CourseSearchRequest, pageable: Pageable): Page<Course> {

        searchRequest.apply {

            val (isName, isStatus, isLevel) = Triple(
                name.isNonNull(), courseStatus.isNonNull(), courseLevel.isNonNull()
            )
            val (isStatusLevel, isStatusName, isLevelName) = Triple(
                isStatus and isLevel, isStatus and isName, isLevel and isName
            )
            val isAllFilled = isStatusLevel and isName

            return when {
                isAllFilled -> courseRepository.findAllByStatusAndLevelAndNameContains(
                    courseStatus!!, courseLevel!!, name!!, pageable
                )

                isStatusLevel -> courseRepository.findAllByStatusAndLevel(
                    courseStatus!!, courseLevel!!, pageable
                )

                isStatusName -> courseRepository.findAllByStatusAndNameContains(
                    courseStatus!!, name!!, pageable
                )

                isLevelName -> courseRepository.findAllByLevelAndNameContains(
                    courseLevel!!, name!!, pageable
                )

                isStatus -> courseRepository.findAllByStatus(courseStatus!!, pageable)
                isLevel -> courseRepository.findAllByLevel(courseLevel!!, pageable)
                isName -> courseRepository.findAllByNameContains(name!!, pageable)

                else -> courseRepository.findAll(pageable)
            }
        }

    }

    @Transactional
    override fun saveSubscription(
        courseId: UUID,
        subscriptionRequest: CourseSubscriptionRequest
    ): User {

        subscriptionRequest.apply {
            val userIsAlreadyRegistered = courseRepository.existsByCourseAndUser(
                courseId,
                userId
            )

            if (userIsAlreadyRegistered) throw CourseUserSubscriptionException(
                """Error: Subscription already""", CONFLICT
            )

            val user = userService.find(userId)

            if(user.isBlocked())
                throw CourseUserSubscriptionException("Error: User is blocked", UNAUTHORIZED)

            val course = find(courseId)
            course.users.add(user)
            courseRepository.save(course)

            logger.info("User #$userId enrolled in the course #$courseId")
            return user
        }
    }

    private fun validateUpdateRequest(updateRequest: CourseUpdateRequest) {

        updateRequest.apply {

            if (name != null) validateCourseName(name)
            if (description != null) validateCourseDescription(description)
        }
    }

    private fun validateCreateRequest(courseCreateRequest: CourseRegistrationRequest) {

        courseCreateRequest.apply {
            validateInstructor(instructorId)
            validateCourseName(name)
            validateCourseDescription(description)
        }
    }

    private fun validateInstructor(instructorId: UUID) {

        val user = userService.find(instructorId)
        if (user.isStudent())
            throw CourseRegistrationException("User must be INSTRUCTOR or ADMIN", UNAUTHORIZED)
    }

    private fun validateCourseDescription(description: String) {
        if (description.isBlank()) throw CourseRegistrationException(
            "The description is not valid",
            CONFLICT
        )
    }

    private fun validateCourseName(name: String) {

        val (minSizeName, maxSizeName) = Pair(2, 30)
        if (name.isSizeExceedLimit(minSizeName, maxSizeName)) throw CourseException(
            "Name must be between $minSizeName and $maxSizeName characters", CONFLICT
        )
    }

    fun String.isSizeExceedLimit(lowerLimit: Int, upperLimit: Int): Boolean {
        if (lowerLimit > upperLimit) throw NumberFormatException("The lower limit cannot be greater than the upper limit.")
        return this.length < lowerLimit || this.length > upperLimit
    }

}