package com.busticket.booking.dto

import com.busticket.booking.entity.Voyage
import javax.persistence.Id

class ScheduleTemplateDto(
        val id: Int,
        val timeStart: Long,
        val timeEnd: Long,
        var voyages: Set<VoyageDto> = setOf(),
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}