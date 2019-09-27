package com.busticket.booking.request

import javax.validation.constraints.NotNull

class CustomerRequest(
        @field:NotNull
        var name: String,
        @field:NotNull
        var phoneNumber: String
        ) {
}