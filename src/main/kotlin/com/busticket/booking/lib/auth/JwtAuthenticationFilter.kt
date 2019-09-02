package com.busticket.booking.lib.auth

import com.busticket.booking.service.interfaces.AuthService
import com.busticket.booking.lib.exception.ExecuteException
import org.springframework.beans.factory.annotation.Autowired
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

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = request.getHeader(AUTHORIZATION)?.trim()
        // This filter run 2 times for a request somehow
        // So we should check the request attribute first, if it is exists, we can make it pass immediately
        val user =
            when {
                request.getAttribute(USER_ATTR_NAME) != null -> request.getAttribute("user")
                token != null -> try {
                    authService.decodeToken(token).get()
                } catch (e: Exception) {
                    throw UsernameNotFoundException("wrong_authentication_info")
                }
                else -> null
            }
        val requestAuthentication = UsernamePasswordAuthenticationToken(user, user)
        request.setAttribute(USER_ATTR_NAME, user)
        return authenticationManager.authenticate(requestAuthentication)
    }

    override fun successfulAuthentication(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain, authResult: Authentication) {
        SecurityContextHolder.getContext().authentication = authResult
        chain.doFilter(request, response)
    }
}