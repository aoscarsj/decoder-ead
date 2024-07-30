package com.ead.notificationservice.infrastructure.adapter.mapper.impl

import com.ead.notificationservice.domain.notification.entity.Notification
import com.ead.notificationservice.infrastructure.adapter.mapper.NotificationMapper
import com.ead.notificationservice.infrastructure.entity.NotificationEntity
import org.springframework.stereotype.Component

@Component
class NotificationMapperImpl : NotificationMapper {
    override fun toDomain(notificationEntity: NotificationEntity): Notification =
        with(notificationEntity) {
            Notification(
                notificationId = notificationId,
                title = title,
                message = message,
                createdAt = createdAt,
                updatedAt = updatedAt,
                status = status,
                userId = userId
            )
        }

    override fun toEntity(notification: Notification): NotificationEntity =
        with(notification) {
            NotificationEntity(
                notificationId = notificationId,
                title = title,
                message = message,
                createdAt = createdAt,
                updatedAt = updatedAt,
                status = status,
                userId = userId
            )
        }
}