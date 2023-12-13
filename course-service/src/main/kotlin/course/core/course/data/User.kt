package course.core.course.data

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS")
data class User(

    @Id @Column(nullable = false) val userId: UUID,

) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
        fun from(userEvent: UserEvent): User =
            with(userEvent) {
                User(
                    userId = userId
                )
            }
    }
}