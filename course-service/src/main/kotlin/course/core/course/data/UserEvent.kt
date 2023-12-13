package course.core.course.data

import java.util.*

data class UserEvent(
    val userId: UUID,
    val username: String,
    val email: String,
    val fullName: String,
    val userStatus: String,
    val userType: String,
    val phoneNumber: String,
    val cpf: String,
    val imageUrl: String,
    var actionType: ActionType,
)