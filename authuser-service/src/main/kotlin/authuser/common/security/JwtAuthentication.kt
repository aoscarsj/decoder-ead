package authuser.common.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class JwtAuthentication(
    private var authenticated: Boolean,
    private val name: String,
    private val credentials: String,
    private val authorities: MutableList<out GrantedAuthority>
) : Authentication {

    override fun getName(): String {
        return name
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return authorities
    }

    override fun getCredentials(): Any {
        return credentials
    }

    override fun getDetails(): Any? {
        return null
    }

    override fun getPrincipal(): Any {
        return name
    }

    override fun isAuthenticated(): Boolean {
        return authenticated
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        isAuthenticated.also { this.authenticated = it }
    }

}