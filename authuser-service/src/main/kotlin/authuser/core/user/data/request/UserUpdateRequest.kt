package authuser.core.user.data.request

import authuser.core.user.data.User
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserUpdateRequest(

    @JsonView(UserView.Companion.UserPut::class)
    val email: String? = null,

    @JsonView(UserView.Companion.PasswordPut::class)
    @Size(min = 8, max = 25, groups = [UserView.Companion.PasswordPut::class])
    val password: String? = null,

    @JsonView(UserView.Companion.PasswordPut::class)
    @Size(min = 8, max = 25, groups = [UserView.Companion.PasswordPut::class])
    val oldPassword: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val fullName: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val phoneNumber: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val cpf: String? = null,

    @JsonView(UserView.Companion.ImagePut::class)
    val imageUrl: String? = null,

    @JsonView(UserView.Companion.UserPut::class)
    val status: User.UserStatus? = null
) {

    interface UserView {
        companion object {
            interface UserPut
            interface PasswordPut
            interface ImagePut
        }
    }
}