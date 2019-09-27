package com.busticket.booking.dto

class CustomerTypeDto(
        var id: Int,
        var name: String,
        var description: String? = null,
        var discount: Int,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}