package authuser.core.user.data

data class UserSearchRequest(
    val email: String? = null,
    val type: UserType? = null,
    val status: UserStatus? = null
)