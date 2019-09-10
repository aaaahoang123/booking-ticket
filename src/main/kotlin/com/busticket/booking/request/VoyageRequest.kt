package com.busticket.booking.request

import org.hibernate.validator.constraints.Length
import javax.validation.Valid
import javax.validation.constraints.NotEmpty

class VoyageRequest(
        @field:NotEmpty
        var name: String = "",
        @field:NotEmpty
        @field:Valid
//        @field:Length(min = 1)
        var voyagePartRequests: List<VoyagePartRequest> = listOf()
) {
}