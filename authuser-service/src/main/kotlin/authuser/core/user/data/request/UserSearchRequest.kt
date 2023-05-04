package authuser.core.user.data.request

import authuser.core.user.data.User.UserStatus
import authuser.core.user.data.User.UserType
import java.util.*

data class UserSearchRequest(
    val email: String? = null,
    val type: UserType? = null,
    val status: UserStatus? = null,
    val courseId: UUID? = null
)