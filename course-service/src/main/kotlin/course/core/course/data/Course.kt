package course.core.course.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import course.core.course.data.request.CourseRegistrationRequest
import course.core.module.data.Module
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_COURSES")
data class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var courseId: UUID? = null,
    @Column(nullable = false, length = 150)
    var name: String,
    @Column(nullable = false, length = 250)
    var description: String,
    @Column
    var imageUrl: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: CourseStatus,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var level: CourseLevel,
    @Column(nullable = false)
    var instructorId: UUID,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_LOCAL_DATE)
    var created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_LOCAL_DATE)
    val updated: LocalDateTime? = null,

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(
        mappedBy = "course", fetch = FetchType.LAZY
    )
    @Fetch(FetchMode.SUBSELECT)
    val modules: MutableSet<Module> = mutableSetOf()
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1L
        private const val ISO_LOCAL_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun from(courseCreateRequest: CourseRegistrationRequest): Course {

            courseCreateRequest.apply {
                return Course(
                    name = name,
                    description = description,
                    imageUrl = imageUrl,
                    status = status,
                    level = level,
                    instructorId = instructorId
                )
            }
        }
    }
}

enum class CourseStatus { IN_PROGRESS, CONCLUDED }
enum class CourseLevel { BEGINNER, INTERMEDIARY, ADVANCED }

/* COMMENTS
- @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = [CascadeType.ALL],
        orphanRemoval = true) // CascadeType.ALL, orphan => bad performance when there is a lot
        of data, in this case the responsibility for deletion lies with the JPA.
OR
- @OnDelete(action = OnDeleteAction.CASCADE) -> BD is responsibility for deletion, is better, 2
deletions, 1 to course and 1 to all modules
OR
- better is cascade deletion by code.

-@Fetch(FetchMode.SUBSELECT) //SUBSELECT -> 1 search to modules and 1 search to all, JOIN -> 1 to
 all but lazy doesn't work, SELECT -> N searches (bad), default is JOIN.
 */