package com.busticket.booking.dto

class VehicleCategoryDto(
        var id: Int,
        var name: String,
        var seatQuantity: Int,
        var price: Int,
        var createdAt: Long,
        var updatedAt: Long,
        var createdAtStr: String,
        var updatedAtStr: String,
        var vehicles: List<VehicleDto> = listOf(),
        var status: Int
) {
}