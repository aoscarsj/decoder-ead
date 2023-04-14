package course

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CourseServiceApplication

fun main(args: Array<String>) {
    runApplication<CourseServiceApplication>(*args)
}
