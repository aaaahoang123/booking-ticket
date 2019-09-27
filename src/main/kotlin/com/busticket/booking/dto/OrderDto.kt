package com.busticket.booking.dto

class OrderDto(
        var id: Int,
        var customer: CustomerDto? = null,
        var createdBy: UserDto? = null,
//        var schedule: ScheduleDto? = null,
        var finalPrice: Double,
        var paidStatus: Int,
        var orderDetails: List<OrderDetailDto>? = mutableListOf(),
        var customerId: Int? = null,
        var scheduleId: Int? = null,
        var createdById: Int? = null,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}