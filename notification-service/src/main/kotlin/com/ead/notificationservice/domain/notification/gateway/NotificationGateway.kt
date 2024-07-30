package com.ead.notificationservice.domain.notification.gateway

import com.ead.notificationservice.domain.notification.entity.Notification
import java.util.*

interface NotificationGateway {

    fun create(notification: Notification): Notification
    fun find(notificationId: UUID): Notification?
}