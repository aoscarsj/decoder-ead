package authuser.core.auth.rest.v1

import authuser.common.rest.RestResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.context.config.annotation.RefreshScope
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController("/test")
@RefreshScope
class RefreshScope {

    @Value("\${authuser.refreshScope.name}")
    private lateinit var name: String

    @RequestMapping("/refresh-scope")
    fun refreshScope(): RestResponse<String> {
        return RestResponse(message = "Refresh scope is $name", response = name)
    }

}