package com.ead.notificationservice.domain.notification.entity

import com.ead.notificationservice.domain.notification.enums.NotificationStatus
import java.time.OffsetDateTime
import java.util.*

data class Notification(
    val notificationId: UUID,
    val userId: UUID,
    val message: String,
    val title: String,
    val status: NotificationStatus,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime

)
