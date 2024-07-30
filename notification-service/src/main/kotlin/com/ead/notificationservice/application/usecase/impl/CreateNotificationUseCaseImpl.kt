package com.ead.notificationservice.application.usecase.impl

import com.ead.notificationservice.application.usecase.CreateNotificationUseCase
import com.ead.notificationservice.domain.notification.gateway.NotificationGateway

class CreateNotificationUseCaseImpl(
    private val notificationGateway: NotificationGateway
) : CreateNotificationUseCase {
    override fun execute() {
        TODO("Not yet implemented")
    }
}