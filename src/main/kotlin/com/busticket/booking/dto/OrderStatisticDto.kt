package com.busticket.booking.dto

class OrderStatisticDto(
        var paidStatus: Int ? = 0,
        var total: Int ? = 0,
        var totalRevenue: Double ? = 0.0
) {
}