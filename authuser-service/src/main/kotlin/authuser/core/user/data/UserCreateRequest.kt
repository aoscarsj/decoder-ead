package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserCreateRequest(

    @JsonView(UserView.Companion.RegistrationPost::class)
    val username: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val email: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val password: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val fullName: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val phoneNumber: String,

    @JsonView(UserView.Companion.RegistrationPost::class)
    val cpf: String,
) {

    interface UserView {
        companion object {
            interface RegistrationPost
        }
    }
}