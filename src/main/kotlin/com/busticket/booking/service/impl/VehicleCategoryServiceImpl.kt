package com.busticket.booking.service.impl

import com.busticket.booking.entity.VehicleCategory
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.vehicle.VehicleCategoryRepository
import com.busticket.booking.request.VehicleCategoryRequest
import com.busticket.booking.service.interfaces.VehicleCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class VehicleCategoryServiceImpl @Autowired constructor(
        private val vehicleCategoryRepository: VehicleCategoryRepository
) : VehicleCategoryService, BaseService<VehicleCategory, Int>() {

    init {
        primaryRepo = vehicleCategoryRepository
    }

    override fun getInstanceClass(): KClass<VehicleCategory> {
        return VehicleCategory::class
    }

    override fun create(dto: Any): VehicleCategory {
        dto as VehicleCategoryRequest
        val vehicleCategory = assignObject(VehicleCategory(), dto)
        return primaryRepo.save(vehicleCategory)
    }
}