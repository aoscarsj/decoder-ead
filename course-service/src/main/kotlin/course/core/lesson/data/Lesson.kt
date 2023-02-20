package course.core.lesson.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import course.core.lesson.data.request.LessonRegistrationRequest
import course.core.module.data.Module
import java.io.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_LESSONS")
data class Lesson(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var lessonId: UUID? = null,
    @Column(nullable = false, length = 150)
    var title: String,
    @Column(nullable = false, length = 250)
    var description: String,
    @Column(nullable = false)
    var videoUrl: String,
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_LOCAL_DATE)
    val created: LocalDateTime = LocalDateTime.now(ZoneId.of("UTC")),

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val module: Module
) : Serializable {

    companion object {
        private const val serialVersionUID: Long = 1L
        private const val ISO_LOCAL_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'"

        fun from(lessonRegistrationRequest: LessonRegistrationRequest, module: Module): Lesson =
            with(lessonRegistrationRequest) {
                Lesson(
                    title = title,
                    description = description,
                    videoUrl = videoUrl,
                    module = module
                )
            }
    }
}