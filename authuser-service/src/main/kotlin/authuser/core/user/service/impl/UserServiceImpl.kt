package authuser.core.user.service.impl

import authuser.common.extension.isCPF
import authuser.common.extension.isEmail
import authuser.common.extension.isUsername
import authuser.common.rest.RestException
import authuser.common.rest.RestItemError
import authuser.core.user.data.User
import authuser.core.user.data.UserCreateRequest
import authuser.core.user.data.UserSearchRequest
import authuser.core.user.data.UserUpdateRequest
import authuser.core.user.exception.PasswordException
import authuser.core.user.exception.UserException
import authuser.core.user.exception.UserRegistrationException
import authuser.core.user.repository.UserRepository
import authuser.core.user.service.UserService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.HttpStatus.*
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    private val passwordEncoder: BCryptPasswordEncoder = BCryptPasswordEncoder()
    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    override fun findAll(searchRequest: UserSearchRequest, page: Pageable): Page<User> {

        logger.info("Searching Records with the request #$searchRequest")
        searchRequest.apply {

            val (hasEmail, hasStatus, hasType) = Triple(email != null, status != null, type != null)
            val (hasStatusType, hasStatusEmail, hasTypeEmail) = Triple(
                hasStatus && hasType, hasEmail && hasStatus, hasType && hasEmail
            )
            val isAllFilled = hasStatusType && hasEmail

            return when {

                isAllFilled -> userRepository.findAllByTypeAndStatusAndEmailContains(
                    type!!, status!!, email!!, page
                )

                hasStatusEmail -> userRepository.findAllByStatusAndEmailContains(
                    status!!, email!!, page
                )

                hasStatusType -> userRepository.findAllByTypeAndStatus(type!!, status!!, page)
                hasTypeEmail -> userRepository.findAllByTypeAndEmailContains(type!!, email!!, page)
                hasStatus -> userRepository.findAllByStatus(status!!, page)
                hasType -> userRepository.findAllByType(type!!, page)
                hasEmail -> userRepository.findAllByEmailContains(email!!, page)

                else -> userRepository.findAll(page)
            }
        }
    }


    override fun findById(userId: UUID): User {

        logger.info("Searching user by userId: #$userId")
        return userRepository.findByIdOrNull(userId) ?: throw UserException(
            "User not found",
            NOT_FOUND
        )
    }

    override fun delete(userId: UUID) {

        logger.warn("Deleting user by userId: #$userId")
        return userRepository.delete(findById(userId))
    }

    override fun existsByUsername(user: User): Boolean =
        userRepository.existsByUsername(user.username)


    override fun update(userId: UUID, updateRequest: UserUpdateRequest): User {

        logger.info("Starting user update")
        validateRequest(updateRequest)

        val user = findById(userId)

        logger.info("user to be changed: $user")
        updateRequest.apply {

            if (!email.isNullOrEmpty())
                user.email = email
            if (!fullName.isNullOrEmpty())
                user.fullName = fullName
            if (!phoneNumber.isNullOrEmpty())
                user.phoneNumber = phoneNumber
            if (!cpf.isNullOrEmpty())
                user.cpf = cpf
        }

        logger.info("Saving updated user: $user")
        return userRepository.save(user)
    }

    private fun validateRequest(request: UserUpdateRequest) {

        logger.info("Starting validate request")

        val errors: MutableList<RestItemError> = mutableListOf()
        val errorCode = "UPDATE_USER"

        request.apply {

            email?.let {

                if (!email.isEmail()) {
                    logger.warn("Email: #$email is not valid")
                    errors.add(RestItemError("Email is not valid", code = "${errorCode}_E01"))
                }
                if (userRepository.existsByEmail(email)) {
                    logger.warn("Email: #$email already registered")
                    errors.add(RestItemError("email already registered", code = "${errorCode}_E02"))
                }
            }

            cpf?.let {
                if (!cpf.isCPF()) {
                    logger.warn("CPF: #$cpf is not valid")
                    errors.add(RestItemError("CPF is not valid", code = "${errorCode}_C01"))
                }
                if (userRepository.existsByCpf(cpf)) {
                    logger.warn("CPF: #$cpf already registered")
                    errors.add(RestItemError("cpf already registered", code = "${errorCode}_E02"))
                }
            }
        }

        if (errors.isNotEmpty()) {
            logger.error("The request for CPF #${request.cpf} is not valid, errors: $errors")
            throw UserRegistrationException("The request is not valid", CONFLICT, errors)
        }
    }

    override fun updatePassword(userId: UUID, updateRequest: UserUpdateRequest) {

        logger.info("Starting password update for userId #$userId")
        val user = findById(userId)
        validatePassword(user, updateRequest)

        user.password = passwordEncoder.encode(updateRequest.password)
        userRepository.save(user)
    }

    private fun validatePassword(user: User, updateRequest: UserUpdateRequest) {

        logger.info("Starting password validate for userId #${user.userId}")
        updateRequest.apply {
            if (oldPassword.isNullOrEmpty() || password.isNullOrEmpty()) {
                logger.error("Password for user #${user.userId} cannot be null or empty")
                throw PasswordException("Password cannot be null or empty.", BAD_REQUEST)
            }
            if (password.length < 8 || password.length > 25) {
                logger.error("Password for user #${user.userId} must be greater than 7 digits")
                throw PasswordException("Password must be between 8 and 25 digits.", BAD_REQUEST)
            }
            if (passwordEncoder.matches(oldPassword, user.password).not()) {
                logger.error("Incorrect password for user #${user.userId}")
                throw PasswordException("Error: Mismatched old password.", CONFLICT)
            }
        }
    }

    override fun updateImage(userId: UUID, updateRequest: UserUpdateRequest): User {

        val user = findById(userId)

        if (updateRequest.imageUrl.isNullOrEmpty())
            throw UserException("ImageUrl cannot be null or empty", BAD_REQUEST)

        user.imageUrl = updateRequest.imageUrl
        userRepository.save(user)

        return user
    }


    override fun signup(request: UserCreateRequest): User {

        logger.info("signup request started")
        validateSignupRequest(request)
        val user = User.from(request)

        validateUserInformation(user)

        user.password = passwordEncoder.encode(user.password)
        userRepository.save(user)

        logger.info("signup for userId: ${user.userId} completed successfully")
        return user
    }

    private fun validateSignupRequest(request: UserCreateRequest) {

        logger.info("validate signup request started")
        val errors: MutableList<RestItemError> = mutableListOf()
        val errorCode = "REGISTRATION_USER"
        val (minUsernameSize, maxUsernameSize) = Pair(4, 30)
        val (minPasswordSize, maxPasswordSize) = Pair(8, 25)

        val usernameSizeInvalidMessage =
            """Username must be between $minUsernameSize and $maxUsernameSize characters"""
        val passwordSizeInvalidMessage =
            """Password must be between $minPasswordSize and $maxPasswordSize characters"""
        val usernameContainsSpaceMessage = "The username is not valid"
        val emailInvalidMessage = "The email is not valid"
        val cpfInvalidMessage = "The CPF is not valid"

        request.apply {

            if (username.length < minUsernameSize || username.length > maxUsernameSize)
                errors.add(RestItemError(usernameSizeInvalidMessage, "${errorCode}_001"))
            if (username.isUsername())
                errors.add(RestItemError(usernameContainsSpaceMessage, "${errorCode}_002"))
            if (email.isEmail().not())
                errors.add(RestItemError(emailInvalidMessage, "${errorCode}_003"))
            if (password.length < minPasswordSize || password.length > maxPasswordSize)
                errors.add(RestItemError(passwordSizeInvalidMessage, "${errorCode}_004"))
            if (cpf.isCPF().not())
                errors.add(RestItemError(cpfInvalidMessage, "${errorCode}_005"))
        }

        if (errors.isNotEmpty()) {

            logger.error("Error validating user data, errors: $errors")
            throw UserRegistrationException(
                "The request is not valid", CONFLICT, errors
            )
        }
    }


    private fun validateUserInformation(user: User): Boolean {

        logger.info("Starting validate user information for userId #${user.userId}")
        if (existsByUsername(user)) {
            logger.error("Username #${user.username} is already taken")
            throw RestException(
                message = "Error: Username is already taken!",
                httpStatus = CONFLICT,
            )
        }
        if (userRepository.existsByEmail(user.email)) {
            logger.error("Email #${user.email} is already taken")
            throw RestException(message = "Error: Email is Already taken!", httpStatus = CONFLICT)
        }

        return true
    }
}
