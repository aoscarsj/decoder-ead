package course.core.course.data

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES_USERS")
data class CourseUser(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val courseUserId: UUID,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val course: Course,

    @Column(nullable = false)
    val userId: UUID
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}