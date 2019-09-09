package com.busticket.booking.service.interfaces

import com.busticket.booking.dto.UserDto
import com.busticket.booking.dto.VoyageDto
import com.busticket.booking.dto.VoyagePartDto
import com.busticket.booking.entity.User
import com.busticket.booking.entity.Voyage
import com.busticket.booking.entity.VoyagePart

interface DtoBuilderService {
    fun buildUserDto(user: User, token: String? = null): UserDto
    fun buildVoyageDto(voyage: Voyage): VoyageDto
    fun buildVoyagePartDto(voyagePart: VoyagePart): VoyagePartDto
}