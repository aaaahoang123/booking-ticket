package com.busticket.booking.lib.auth

import com.busticket.booking.entity.Member
import com.busticket.booking.service.interfaces.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*
@Component
class AuthenticationProvider @Autowired constructor(
       private val authService: AuthService
): AbstractUserDetailsAuthenticationProvider() {
    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        val member = authentication?.credentials
        if (member != null) {
            return authService.buildUserDetailFromMember(member as Member)
        }
        throw UsernameNotFoundException("wrong_authentication_info")
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {
        //
    }
}