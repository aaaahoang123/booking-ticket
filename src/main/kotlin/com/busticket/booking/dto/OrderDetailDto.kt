package com.busticket.booking.dto

class OrderDetailDto(
        var orderId: Int? = null,
        var vehicleCategoryId: Int? = null,
//        var customerType: CustomerTypeDto? = null,
        var customerTypeId: Int,
        var travelFrom: VoyagePartDto? = null,
        var travelTo: VoyagePartDto? = null,
        var travelFromId: Int? = null,
        var travelToId: Int? = null,
        var unitPrice: Int,
        var originTotalPrice: Double,
        var totalPrice: Double,
        var quantity: Int,
        var discount: Int,
        val createdAt: Long,
        val updatedAt: Long,
        val createdAtStr: String,
        val updatedAtStr: String,
        val status: Int
) {
}