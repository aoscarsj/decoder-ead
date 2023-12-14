package course.core.course.consumers

import course.core.course.data.ActionType.*
import course.core.course.data.User
import course.core.course.data.UserEvent
import course.core.course.service.UserService
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component

@Component
class UserConsumer(
    private val userService: UserService
) {

    @RabbitListener(
        bindings = [
            QueueBinding(
                value = Queue(value = "\${ead.broker.queue.userEventQueue.name}", durable = "true"),
                exchange = Exchange(
                    value = "\${ead.broker.exchange.userEventExchange}", type = ExchangeTypes.FANOUT
                )
            )
        ]
    )
    fun listenUserEvent(@Payload userEvent: UserEvent) {
        val user = User.from(userEvent)

        when (userEvent.actionType) {
            CREATE, UPDATE -> userService.save(user)
            DELETE -> userService.delete(user.userId)
        }
    }
}