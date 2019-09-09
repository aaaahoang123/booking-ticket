package com.busticket.booking.dto

import com.busticket.booking.entity.VoyagePart

class VoyageDto(
        val id: Int,
        val name: String,
        val createdAt: Long,
        val updatedAt: Long,
        val status: Int,
        var voyageParts: List<VoyagePartDto> = listOf()
) {
}