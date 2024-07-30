package com.ead.notificationservice.infrastructure.repository

import com.ead.notificationservice.infrastructure.entity.NotificationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface NotificationRepository : JpaRepository<NotificationEntity, UUID> {
    fun findByNotificationId(notificationId: UUID): NotificationEntity?
}