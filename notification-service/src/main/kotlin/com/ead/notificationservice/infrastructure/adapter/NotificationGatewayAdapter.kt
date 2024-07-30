package com.ead.notificationservice.infrastructure.adapter

import com.ead.notificationservice.domain.notification.entity.Notification
import com.ead.notificationservice.domain.notification.gateway.NotificationGateway
import com.ead.notificationservice.infrastructure.adapter.mapper.NotificationMapper
import com.ead.notificationservice.infrastructure.repository.NotificationRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class NotificationGatewayAdapter(
    private val notificationRepository: NotificationRepository,
    private val mapper: NotificationMapper
) : NotificationGateway {
    override fun create(notification: Notification): Notification {
        return mapper.toDomain(notificationRepository.save(mapper.toEntity(notification)))
    }

    override fun find(notificationId: UUID): Notification? {
        return notificationRepository.findByNotificationId(notificationId)
            ?.let { mapper.toDomain(it) }
    }
}