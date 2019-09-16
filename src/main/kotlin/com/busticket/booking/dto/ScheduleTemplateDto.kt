package com.busticket.booking.dto

import javax.persistence.Id

class ScheduleTemplateDto(
        val id: Int,
        val timeStart: Long,
        val timeEnd: Long,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}