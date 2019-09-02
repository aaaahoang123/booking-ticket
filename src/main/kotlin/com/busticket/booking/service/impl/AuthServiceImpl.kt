package com.busticket.booking.service.impl

import com.busticket.booking.repository.user.UserRepository
import com.busticket.booking.repository.role.UserRoleRepository
import com.busticket.booking.request.AuthRequest
import com.busticket.booking.service.interfaces.AuthService
import com.busticket.booking.lib.assignObject
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.user.memberEmailEqual
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import io.jsonwebtoken.*
import org.springframework.security.crypto.password.PasswordEncoder


@Service
class AuthServiceImpl @Autowired constructor(
        private val userRepo: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val userRoleRepository: UserRoleRepository
) : AuthService {

    private val _jwtSecret = "adjkfhasf897235hjgasfdghjsat612dfasjk"
    private val _jwtExpiration = 604800000L

    override fun register(dto: AuthRequest): com.busticket.booking.entity.User {
        if (userRepo.count(memberEmailEqual(dto.email)) > 0) {
            throw ExecuteException("duplicate_email")
        }
        dto.password = passwordEncoder.encode(dto.password)
        val member = assignObject(com.busticket.booking.entity.User(), dto)
        return userRepo.save(member)
    }

    override fun attemptMember(email: String, password: String): com.busticket.booking.entity.User {
        val oMember = userRepo.findMemberByEmail(email)
        if (!oMember.isPresent) {
            throw ExecuteException("account_not_exist")
        }
        val member = oMember.get()
        if (!passwordEncoder.matches(password, member.password)) {
            throw ExecuteException("wrong_password")
        }
        return member
    }

    override fun generateToken(user: com.busticket.booking.entity.User): String {
        val now = Date()
        val expiryDate = Date(now.time + _jwtExpiration)
        // Tạo chuỗi json web token từ id của user.
        return Jwts.builder()
                .setSubject(user.id.toString())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, _jwtSecret)
                .compact()
    }

    override fun decodeToken(token: String): Optional<com.busticket.booking.entity.User> {
        validateToken(token)
        val claims = Jwts.parser()
                .setSigningKey(_jwtSecret)
                .parseClaimsJws(token)
                .body
                .subject
        val id = Integer.parseInt(claims, 10)
        return userRepo.findMemberByIdAndFetchPolicy(id)
    }

    @Throws(exceptionClasses = [MalformedJwtException::class, ExpiredJwtException::class, UnsupportedJwtException::class, IllegalArgumentException::class])
    private fun validateToken(authToken: String): Boolean {
        Jwts.parser().setSigningKey(_jwtSecret).parseClaimsJws(authToken)
        return true
    }
}