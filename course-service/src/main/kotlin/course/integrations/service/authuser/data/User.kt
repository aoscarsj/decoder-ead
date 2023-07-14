package course.integrations.service.authuser.data

import java.util.*

data class User(
    var userId: UUID?,
    val username: String,
    var email: String,
    var fullName: String,
    val status: UserStatus,
    val type: UserType,
    var phoneNumber: String,
    var cpf: String,
    var imageUrl: String,
    ) {
    enum class UserStatus { ACTIVE, BLOCKED }
    enum class UserType { ADMIN, STUDENT, INSTRUCTOR }
}
data class UserCourse(

    val userCourseId: UUID? = null,
    val user: User,
    val courseId: UUID
)