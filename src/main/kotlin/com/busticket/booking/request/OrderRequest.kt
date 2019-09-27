package com.busticket.booking.request

import javax.validation.constraints.NotNull

class OrderRequest(
        @field:NotNull
        var customerName: String,
        @field:NotNull
        var phoneNumber: String,
        @field:NotNull
        var scheduleId: Int,
        @field:NotNull
        var paidStatus: Int,
        @field:NotNull
        var orderDetailRequest: List<OrderDetailRequest> = mutableListOf()
) {
}