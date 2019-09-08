package com.busticket.booking.request

import com.busticket.booking.enum.status.CommonStatus
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class UserRequest(
        var id: Int? = null,
        @NotEmpty
        @Email
        var email: String = "",
        var password: String? = null,
        var name: String? = null,
        var phoneNumber: String? = null,
        var address: String? = null,
        var avatar: String? = null,
        var birthday: Long? = null,
        var gender: Int? = null,
        var status: Int = CommonStatus.ACTIVE.value
)