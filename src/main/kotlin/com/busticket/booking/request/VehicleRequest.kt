package com.busticket.booking.request

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

class VehicleRequest(
        @field:NotEmpty
        var name: String = "",
        @field:NotEmpty
        var plate: String,
        var color: String,
        @field:NotNull
        var category_id: Int

) {

}