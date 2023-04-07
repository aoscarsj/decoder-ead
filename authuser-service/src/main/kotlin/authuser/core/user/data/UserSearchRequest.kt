package authuser.core.user.data

import java.util.UUID

data class UserSearchRequest(
    val email: String? = null,
    val type: UserType? = null,
    val status: UserStatus? = null,
    val courseId: UUID? = null
)