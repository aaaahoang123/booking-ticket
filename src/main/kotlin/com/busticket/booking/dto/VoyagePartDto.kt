package com.busticket.booking.dto

class VoyagePartDto(
        var id: Int,
        var fromId: Int,
        var fromName: String,
        var toId: Int,
        var toName: String,
        var distance: Int,
        var orderNumber: Int,
        var createdAt: Long,
        var createdAtStr: String,
        var updatedAtStr: String,
        var updatedAt: Long,
        var status: Int
) {
}