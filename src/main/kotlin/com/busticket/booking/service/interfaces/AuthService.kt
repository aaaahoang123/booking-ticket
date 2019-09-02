package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.Member
import com.busticket.booking.request.AuthRequest
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

interface AuthService {
    fun register(dto: AuthRequest): Member
    fun attemptMember(email: String, password: String): Member
    fun generateToken(member: Member): String
    fun decodeToken(token: String): Optional<Member>
    fun getUserDetailByEmail(email: String): UserDetails
    fun buildUserDetailFromMember(member: Member): UserDetails
}