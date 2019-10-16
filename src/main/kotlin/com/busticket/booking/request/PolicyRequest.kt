package com.busticket.booking.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class PolicyRequest(
        @field:NotNull
        @field:NotEmpty
        @field:Size(min = 1)
        var roles: List<String>? = listOf(),
        @field:NotNull
        @field:NotEmpty
        @field:Size(max = 255)
        var name: String? = null
)