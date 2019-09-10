package com.busticket.booking.dto

class VehicleDto(
        val id: Int,
        val name: String,
        val plate: String,
        val color: String,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}