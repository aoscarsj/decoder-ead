package com.ead.notificationservice.infrastructure.adapter.mapper

import com.ead.notificationservice.domain.notification.entity.Notification
import com.ead.notificationservice.infrastructure.entity.NotificationEntity

interface NotificationMapper {
    fun toDomain(notificationEntity: NotificationEntity): Notification

    fun toEntity(notification: Notification): NotificationEntity
}