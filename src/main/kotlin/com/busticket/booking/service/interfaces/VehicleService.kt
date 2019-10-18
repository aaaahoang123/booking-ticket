package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.Vehicle

interface VehicleService: IBaseService<Vehicle, Int> {
    fun getByCategoryId(categoryId: Int): List<Vehicle>
}