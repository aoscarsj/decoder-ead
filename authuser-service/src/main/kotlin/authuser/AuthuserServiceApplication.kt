package authuser

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AuthuserServiceApplication

fun main(args: Array<String>) {
    runApplication<AuthuserServiceApplication>(*args)
}
