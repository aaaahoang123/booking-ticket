package com.busticket.booking.lib.auth

import com.busticket.booking.entity.User
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.service.interfaces.AuthService
import com.busticket.booking.repository.hasStatus
import com.busticket.booking.repository.role.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.lang.Exception
import javax.servlet.FilterChain


class JwtAuthenticationFilter(requestMatcher: RequestMatcher): AbstractAuthenticationProcessingFilter(requestMatcher) {
    @Autowired
    private lateinit var authService: AuthService
    @Autowired
    private lateinit var userRoleRepository: UserRoleRepository

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = request.getHeader(AUTHORIZATION)?.trim()
        // This filter run 2 times for a request somehow
        // So we should check the request attribute first, if it is exists, we can make it pass immediately
        val user =
            when {
                request.getAttribute(USER_ATTR_NAME) != null -> request.getAttribute(USER_ATTR_NAME)
                token != null -> try {
                    var u = authService.decodeToken(token).get()
                    u = fetchRolesForUser(u)
                    request.setAttribute(USER_ATTR_NAME, u)
                    u
                } catch (e: Exception) {
                    throw UsernameNotFoundException("wrong_authentication_info")
                }
                else -> null
            }

        val requestAuthentication = UsernamePasswordAuthenticationToken(user, user)

        return authenticationManager.authenticate(requestAuthentication)
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }

    private fun fetchRolesForUser(user: User): User {
        val policy = user.policy
        if (policy?.specialRole == ADMIN_SPECIAL_ROLE) {
            policy.roles = userRoleRepository.findAll(Specification.where(hasStatus())).toSet()
        }
        user.policy = policy
        return user
    }
}
