package authuser.common.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse, filterChain: FilterChain
    ) {

        if (request.headerNames.toList().contains("role")) {

            val role = request.getHeader("Role")

            val springRole = "ROLE_$role"

            val authorities = mutableListOf(
                SimpleGrantedAuthority(springRole),
                SimpleGrantedAuthority("ROLE_authenticated")
            )

            val auth: Authentication = JwtAuthentication(
                authenticated = true,
                name = springRole,
                credentials = role,
                authorities = authorities
            )

            SecurityContextHolder.getContext().authentication = auth
        }

        filterChain.doFilter(request, response)

    }
}
