package com.busticket.booking.request

import javax.validation.constraints.NotNull

class OutJoinPolicyRequest(
        @field:NotNull
        var userId: Int? = null
) {
}
