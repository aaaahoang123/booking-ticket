package com.busticket.booking.service.impl

import com.busticket.booking.entity.Member
import com.busticket.booking.enum.role.ADMIN_SPECIAL_ROLE
import com.busticket.booking.repository.isActive
import com.busticket.booking.repository.member.MemberRepository
import com.busticket.booking.repository.role.PolicyRoleRepository
import com.busticket.booking.request.AuthRequest
import com.busticket.booking.service.interfaces.AuthService
import com.busticket.booking.lib.assignObject
import com.busticket.booking.lib.exception.ExecuteException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import io.jsonwebtoken.*
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder


@Service
class AuthServiceImpl @Autowired constructor(
        private val memberRepo: MemberRepository,
        private val passwordEncoder: PasswordEncoder,
        private val policyRoleRepository: PolicyRoleRepository
) : AuthService {

    private val _jwtSecret = "adjkfhasf897235hjgasfdghjsat612dfasjk"
    private val _jwtExpiration = 604800000L

    override fun register(dto: AuthRequest): Member {
//        if (memberRepo.count(memberEmailEqual(dto.email)) > 0) {
//            throw Exception("Trùng mail")
//        }
        dto.password = passwordEncoder.encode(dto.password)
        val member = assignObject(Member(), dto)
        return memberRepo.save(member)
    }

    override fun attemptMember(email: String, password: String): Member {
        val oMember = memberRepo.findMemberByEmail(email)
        if (!oMember.isPresent) {
            throw ExecuteException("account_not_exist")
        }
        val member = oMember.get()
        if (!passwordEncoder.matches(password, member.password)) {
            throw ExecuteException("wrong_password")
        }
        return member
    }

    override fun generateToken(member: Member): String {
        val now = Date()
        val expiryDate = Date(now.time + _jwtExpiration)
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(member.id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, _jwtSecret)
                .compact()
    }

    override fun decodeToken(token: String): Optional<Member> {
        validateToken(token)
        val claims = Jwts.parser()
                .setSigningKey(_jwtSecret)
                .parseClaimsJws(token)
                .body
                .subject
        val id = Integer.parseInt(claims, 10)
        return memberRepo.findMemberByIdAndFetchPolicy(id)
    }

    @Throws(exceptionClasses = [MalformedJwtException::class, ExpiredJwtException::class, UnsupportedJwtException::class, IllegalArgumentException::class])
    private fun validateToken(authToken: String): Boolean {
        Jwts.parser().setSigningKey(_jwtSecret).parseClaimsJws(authToken)
        return true
    }

    override fun getUserDetailByEmail(email: String): UserDetails {
        val oMember = memberRepo.findMemberByEmail(email)
        if (oMember.isPresent) {
            return buildUserDetailFromMember(oMember.get())
        } else {
            throw ExecuteException("wrong_authentication_info")
        }
    }

    override fun buildUserDetailFromMember(member: Member): UserDetails {
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