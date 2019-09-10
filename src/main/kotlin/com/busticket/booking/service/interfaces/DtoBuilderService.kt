package com.busticket.booking.service.interfaces

import com.busticket.booking.dto.*
import com.busticket.booking.entity.*

interface DtoBuilderService {
    fun buildUserDto(user: User, token: String? = null): UserDto
    fun buildVoyageDto(voyage: Voyage): VoyageDto
    fun buildVoyagePartDto(voyagePart: VoyagePart): VoyagePartDto
    fun buildVehicleCategoryDto(vehicleCategory: VehicleCategory): VehicleCategoryDto
    fun buildVehicleDto(vehicle: Vehicle): VehicleDto
}