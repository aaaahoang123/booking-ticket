package com.busticket.booking.dto

import com.busticket.booking.entity.ScheduleTemplate
import com.busticket.booking.entity.User
import com.busticket.booking.entity.Vehicle
import com.busticket.booking.entity.Voyage

class ScheduleDto(
        var id: Int? = null,
        var startTime: Long? = null,
        var endTime: Long? = null,
        var scheduleTemplate: ScheduleTemplateDto? = null,
        var vehicleCategory: VehicleCategoryDto? = null,
        var voyage: VoyageDto? = null,
        var vehicle: VehicleDto? = null,
        var driver: UserDto? = null,
        var scheduleTemplateId: Int? = null,
        var vehicleCategoryId: Int? = null,
        var voyageId: Int? = null,
        var driverId: Int? = null,
        var vehicleId: Int? = null,
        var createdById: Int? = null,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}