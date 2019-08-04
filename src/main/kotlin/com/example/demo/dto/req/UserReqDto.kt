package com.example.demo.dto.req

import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

class UserReqDto(
        var id: Int? = null,
        var name: String? = null,
        @NotEmpty
        @Email
        var email: String? = null,
        @NotEmpty
        var password: String? = null
)