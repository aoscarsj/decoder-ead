package course.core.module.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import course.core.course.data.Course
import course.core.lesson.data.Lesson
import course.core.module.data.request.ModuleRegistrationRequest
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_MODULES")
data class Module(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var moduleId: UUID? = null,
    @Column(nullable = false, length = 150)
    var title: String,
    @Column(nullable = false, length = 250)
    var description: String,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_LOCAL_DATE)
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false)
    val course: Course,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    val lessons: MutableSet<Lesson> = mutableSetOf()

) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1L
        private const val ISO_LOCAL_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun from(moduleRegistrationRequest: ModuleRegistrationRequest, course: Course): Module =
            with(moduleRegistrationRequest) {
                Module(title = this.title, description = this.description, course = course)
            }

    }
}