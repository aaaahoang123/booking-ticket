package com.busticket.booking.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class OutJoinPolicyRequest(
        @field:NotNull
        @field:NotEmpty
        var userId: Int? = null
) {
}