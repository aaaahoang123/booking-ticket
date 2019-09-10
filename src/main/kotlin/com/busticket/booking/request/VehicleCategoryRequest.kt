package com.busticket.booking.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

class VehicleCategoryRequest(
        @field:NotEmpty
        var name: String = "",
        @field:Min(1)
        var seatQuantity: Int,
        @field:Min(1)
        var price: Int
) {
}