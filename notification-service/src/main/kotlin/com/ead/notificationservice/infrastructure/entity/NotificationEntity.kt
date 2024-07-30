package com.ead.notificationservice.infrastructure.entity

import com.ead.notificationservice.domain.notification.enums.NotificationStatus
import com.ead.notificationservice.domain.utils.ISO_DATE_PATTERN
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.persistence.*
import java.io.Serializable
import java.time.OffsetDateTime
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_NOTIFICATIONS")
data class NotificationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    val notificationId: UUID,
    @Column(nullable = false)
    val userId: UUID,
    @Column(nullable = false)
    val message: String,
    @Column(nullable = false, length = 150)
    val title: String,
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: NotificationStatus,
    @Column(nullable = false)
    @JsonFormat(pattern = ISO_DATE_PATTERN, shape = JsonFormat.Shape.STRING)
    val createdAt: OffsetDateTime,
    @Column(nullable = false)
    @JsonFormat(pattern = ISO_DATE_PATTERN, shape = JsonFormat.Shape.STRING)
    val updatedAt: OffsetDateTime

) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1L
    }
}
