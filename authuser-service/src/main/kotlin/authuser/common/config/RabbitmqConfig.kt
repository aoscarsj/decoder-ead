package authuser.common.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitmqConfig(
    private val cachingConnectionFactory: CachingConnectionFactory,
    @Value("\${ead.broker.exchange.userEvent}")
    private val exchangeUserEvent: String
) {

    @Bean
    fun rabbitTemplate(): RabbitTemplate {
        val template = RabbitTemplate(cachingConnectionFactory)
        template.messageConverter = messageConverter()
        return template
    }

    @Bean
    fun messageConverter(): Jackson2JsonMessageConverter {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        return Jackson2JsonMessageConverter(objectMapper)
    }

    @Bean
    fun fanoutUserEvent(): FanoutExchange {
        return FanoutExchange(exchangeUserEvent)
    }
}