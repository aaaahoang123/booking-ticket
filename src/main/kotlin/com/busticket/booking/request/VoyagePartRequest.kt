package com.busticket.booking.request

import javax.validation.constraints.Min
import javax.validation.constraints.Size
import kotlin.math.min

class VoyagePartRequest(
        @field:Min(1)
        var fromId: Int = 0,
        @field:Min(1)
        var toId: Int = 0,
        @field:Min(1)
        var distance: Int = 0
) {
}