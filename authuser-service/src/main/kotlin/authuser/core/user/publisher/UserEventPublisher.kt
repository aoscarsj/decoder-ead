package authuser.core.user.publisher

import authuser.core.user.data.UserEvent
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class UserEventPublisher(
    private val rabbitTemplate: RabbitTemplate,
    @Value("\${ead.broker.exchange.userEvent}")
    private val exchangeUserEvent: String
) {

    private val logger: Logger by lazy { LogManager.getLogger(this.javaClass) }

    fun publishUserEvent(userEvent: UserEvent) {
        logger.info(
            """Starting the sending of userEvent by exchange
            | m=publishUserEvent, userEvent=$userEvent,
            | exchange=$exchangeUserEvent
        """.trimMargin()
        )
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", userEvent)
        logger.info(
            """userEvent sent by exchange completed.
            | m=publishUserEvent""".trimMargin()
        )
    }
}