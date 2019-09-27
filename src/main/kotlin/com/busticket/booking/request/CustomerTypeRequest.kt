package com.busticket.booking.request

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class CustomerTypeRequest(
        @field:NotNull
        var name: String,
        var description: String? = null,
        @field:NotNull
        @field:Min(0)
        var discount: Int
) {
}