package com.busticket.booking.service.interfaces

import com.busticket.booking.dto.UserDto
import com.busticket.booking.entity.User

interface DtoBuilderService {
    fun buildUserDto(user: User, token: String? = null): UserDto
}