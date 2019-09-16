package com.busticket.booking.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class ScheduleTemplateRequest(
        @field:NotNull
        var timeStart: Long,
        @field:NotNull
        var timeEnd: Long,
        @field:NotNull
        var listVoyageIds: List<Int> = listOf()
) {
}