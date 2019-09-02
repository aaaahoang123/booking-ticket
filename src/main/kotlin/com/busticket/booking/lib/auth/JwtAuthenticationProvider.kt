package com.busticket.booking.lib.auth

import com.busticket.booking.entity.User
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JwtAuthenticationProvider : AbstractUserDetailsAuthenticationProvider() {
    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        val user = authentication?.credentials
        if (user != null) {
            return buildUserDetailFromMember(user as User)
        }
        throw UsernameNotFoundException("wrong_authentication_info")
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {
        //
    }

    private fun buildUserDetailFromMember(user: User): UserDetails {
        val roles = user.policy?.roles?.map { role -> SimpleGrantedAuthority(role.id) } ?: listOf()

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.email)
                .password(user.password)
                .authorities(roles)
                .build()
    }
}