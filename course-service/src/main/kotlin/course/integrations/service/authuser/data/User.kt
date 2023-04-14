package course.integrations.service.authuser.data

import java.time.LocalDateTime
import java.util.*

data class User(
    var userId: UUID?,
    val username: String,
    var email: String,
    var password: String,
    var fullName: String,
    val status: UserStatus,
    val type: UserType,
    var phoneNumber: String,
    var cpf: String,
    var imageUrl: String,
    val created: LocalDateTime,
    val updated: LocalDateTime?

) {
    enum class UserStatus { ACTIVE, BLOCKED }
    enum class UserType { ADMIN, STUDENT, INSTRUCTOR }
}