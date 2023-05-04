package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS_COURSES")
data class UserCourse(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userCourseId: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User,

    @Column(nullable = false)
    val courseId: UUID
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L

        fun from(user: User, courseId: UUID): UserCourse =
            UserCourse(user = user, courseId = courseId)
    }
}