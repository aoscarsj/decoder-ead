package authuser.common.config

import org.springframework.cloud.openfeign.support.PageJacksonModule
import org.springframework.cloud.openfeign.support.SortJacksonModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfigurationFactory {

    @Bean
    fun pageJacksonModule(): com.fasterxml.jackson.databind.Module {
        return PageJacksonModule()
    }

    @Bean
    fun sortJacksonModule(): com.fasterxml.jackson.databind.Module {
        return SortJacksonModule()
    }
}