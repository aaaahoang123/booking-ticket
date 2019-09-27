package com.busticket.booking.dto

class CustomerDto(
        var id: Int,
        var name: String,
        var phoneNumber: String,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}