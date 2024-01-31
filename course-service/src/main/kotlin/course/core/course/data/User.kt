package course.core.course.data

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
data class User(

    @Id
    @Column(nullable = false)
    val userId: UUID,
    @Column(unique = true, length = 50, nullable = false)
    val username: String,
    @Column(unique = true, length = 50, nullable = false)
    val email: String,
    @Column(length = 150, nullable = false)
    val fullName: String,
    @Column(nullable = false)
    val userStatus: String,
    @Column(nullable = false)
    val userType: String,
    @Column(length = 20)
    val phoneNumber: String,
    @Column(length = 20)
    val cpf: String,
    val imageUrl: String?,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var actionType: ActionType,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    val courses: Set<Course> = mutableSetOf()

) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
        fun from(userEvent: UserEvent): User =
            with(userEvent) {
                User(
                    userId = userId,
                    username = username,
                    email = email,
                    fullName = fullName,
                    userStatus = userStatus,
                    userType = userType,
                    phoneNumber = phoneNumber,
                    cpf = cpf,
                    imageUrl = imageUrl,
                    actionType = actionType
                )
            }
    }

    fun isStudent(): Boolean = this.userType == "STUDENT"

    fun isBlocked(): Boolean = this.userStatus == "BLOCKED"

}