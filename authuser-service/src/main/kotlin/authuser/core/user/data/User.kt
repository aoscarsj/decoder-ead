package authuser.core.user.data

import authuser.core.user.data.request.UserCreateRequest
import authuser.core.user.data.request.UserUpdateRequest
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var userId: UUID? = null,
    @Column(unique = true, length = 50, nullable = false)
    val username: String = "",
    @Column(unique = true, length = 50, nullable = false)
    var email: String = "",
    @Column(nullable = false)
    @JsonIgnore
    var password: String = "",
    @Column(length = 150, nullable = false)
    var fullName: String = "",
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVE,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType = UserType.STUDENT,
    @Column(length = 20)
    var phoneNumber: String = "",
    @Column(length = 20)
    var cpf: String = "",
    var imageUrl: String = "",
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    var updated: LocalDateTime? = null,

    ) : RepresentationModel<User>(), Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L

        fun from(request: UserCreateRequest): User {

            request.apply {
                return User(
                    username = username,
                    password = password,
                    cpf = cpf,
                    phoneNumber = phoneNumber,
                    fullName = fullName,
                    email = email,
                )
            }
        }
    }
    fun update(updateRequest: UserUpdateRequest): User {
        this.apply {
            if (!updateRequest.email.isNullOrEmpty())
                email = updateRequest.email
            if (!updateRequest.fullName.isNullOrEmpty())
                fullName = updateRequest.fullName
            if (!updateRequest.phoneNumber.isNullOrEmpty())
                phoneNumber = updateRequest.phoneNumber
            if (!updateRequest.cpf.isNullOrEmpty())
                cpf = updateRequest.cpf
            if (updateRequest.status != null)
                status = updateRequest.status
        }
        return this
    }
    enum class UserStatus { ACTIVE, BLOCKED }
    enum class UserType { ADMIN, STUDENT, INSTRUCTOR }
}