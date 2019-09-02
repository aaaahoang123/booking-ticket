package com.busticket.booking.lib.auth

import com.busticket.booking.entity.Member
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.repository.isActive
import com.busticket.booking.repository.role.PolicyRoleRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component

@Component
class AuthenticationProvider @Autowired constructor(
        private val policyRoleRepository: PolicyRoleRepository
) : AbstractUserDetailsAuthenticationProvider() {
    @Throws(AuthenticationException::class)
    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        val member = authentication?.credentials
        if (member != null) {
            return buildUserDetailFromMember(member as Member)
        }
        throw UsernameNotFoundException("wrong_authentication_info")
    }

    override fun additionalAuthenticationChecks(userDetails: UserDetails?, authentication: UsernamePasswordAuthenticationToken?) {
        //
    }

    fun buildUserDetailFromMember(member: Member): UserDetails {
        val policy = member.policy
        val roles =
                if (policy?.specialRole == ADMIN_SPECIAL_ROLE) {
                    policyRoleRepository.findAll(Specification.where(isActive())).map { role -> SimpleGrantedAuthority(role.id) }
                } else {
                    policy?.roles?.map { role -> SimpleGrantedAuthority(role.id) } ?: listOf()
                }

        return User.builder()
                .username(member.email)
                .password(member.password)
                .authorities(roles)
                .build()
    }
}