package authuser.common.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
@EnableWebSecurity
class ApplicationConfigurerAdapter : WebSecurityConfigurerAdapter(false) {

    override fun configure(http: HttpSecurity) {
        http.csrf()
            .disable()
            .antMatcher("/*/*/admin/**")
            .addFilterBefore(JwtAuthenticationFilter(), BasicAuthenticationFilter::class.java)
    }
}
