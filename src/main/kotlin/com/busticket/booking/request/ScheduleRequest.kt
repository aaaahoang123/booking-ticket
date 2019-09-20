package com.busticket.booking.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

class ScheduleRequest(
        @field:NotNull
        @field:Min(1)
        val scheduleTemplateId: Int? = null,
        @field:NotNull
        @field:Min(1)
        val vehicleCategoryId: Int? = null,
        @field:NotNull
        val scheduleDate: Long? = null,
        @field:Min(1)
        @field:NotNull
        val voyageId: Int? = null,
        @field:Min(1)
        val driverId: Int? = null,
        @field:Min(1)
        val vehicleId: Int? = null
) {
}