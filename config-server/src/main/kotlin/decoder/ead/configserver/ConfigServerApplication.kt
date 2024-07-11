package decoder.ead.configserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.config.server.EnableConfigServer
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
@PropertySource("file:.env")
class ConfigServerApplication

fun main(args: Array<String>) {
    runApplication<ConfigServerApplication>(*args)
}
