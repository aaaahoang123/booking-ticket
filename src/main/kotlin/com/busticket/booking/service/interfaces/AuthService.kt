package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.User
import com.busticket.booking.request.AuthRequest
import java.util.*

interface AuthService {
    fun register(dto: AuthRequest): User
    fun attemptMember(email: String, password: String): User
    fun generateToken(user: User): String
    fun decodeToken(token: String): Optional<User>
}