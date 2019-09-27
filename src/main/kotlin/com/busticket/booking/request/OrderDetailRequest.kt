package com.busticket.booking.request

import javax.validation.constraints.NotNull

class OrderDetailRequest(
        @field:NotNull
        var vehicleCategoryId: Int,
        @field:NotNull
        var customerTypeId: Int,
        @field:NotNull
        var quantity: Int,
        @field:NotNull
        var travelFromId: Int,
        @field:NotNull
        var travelToId: Int
) {
}