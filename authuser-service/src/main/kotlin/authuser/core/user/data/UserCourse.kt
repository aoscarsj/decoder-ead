package authuser.core.user.data

import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS_COURSES")
data class UserCourse(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val userCourseId: UUID,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val user: User,

    @Column(nullable = false)
    val courseId: UUID
)