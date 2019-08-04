package com.example.demo.dto.req

import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty

class ProductReqDto(
        @NotEmpty
        var name: String,
        @NotEmpty
        var sellerId: Int,
        @NotEmpty
        @Min(1)
        var price: Long
)