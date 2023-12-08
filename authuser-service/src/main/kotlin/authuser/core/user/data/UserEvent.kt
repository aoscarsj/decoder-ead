package authuser.core.user.data

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
    var actionType: String,
) {
    companion object {
        fun from(user: User, actionType: ActionType): UserEvent =
            with(user) {
                UserEvent(
                    userId = userId!!,
                    username = username,
                    email = email,
                    fullName = fullName,
                    userStatus = status.toString(),
                    userType = type.toString(),
                    phoneNumber = phoneNumber,
                    cpf = cpf,
                    imageUrl = imageUrl,
                    actionType = actionType.toString()
                )
            }

    }
}
