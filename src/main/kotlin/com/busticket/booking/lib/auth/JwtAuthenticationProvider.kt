package com.busticket.booking.lib.auth

import com.busticket.booking.entity.User
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.repository.isActive
import com.busticket.booking.repository.role.UserRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

class JwtAuthenticationProvider : AbstractUserDetailsAuthenticationProvider() {
    @Autowired
    private lateinit var userRoleRepository: UserRoleRepository

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

    fun buildUserDetailFromMember(user: User): UserDetails {
        val policy = user.policy
        val roles =
                if (policy?.specialRole == ADMIN_SPECIAL_ROLE) {
                    userRoleRepository.findAll(Specification.where(isActive())).map { role -> SimpleGrantedAuthority(role.id) }
                } else {
                    policy?.roles?.map { role -> SimpleGrantedAuthority(role.id) } ?: listOf()
                }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.email)
                .password(user.password)
                .authorities(roles)
                .build()
    }
}