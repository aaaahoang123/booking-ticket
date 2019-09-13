package com.busticket.booking.service.impl

import com.busticket.booking.entity.Vehicle
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.vehicle.VehicleCategoryRepository
import com.busticket.booking.repository.vehicle.VehicleRepository
import com.busticket.booking.request.VehicleRequest
import com.busticket.booking.service.interfaces.VehicleCategoryService
import com.busticket.booking.service.interfaces.VehicleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class VehicleServiceImpl @Autowired constructor(
        private val vehicleRepository: VehicleRepository,
        private val vehicleCategoryRepository: VehicleCategoryRepository
) : VehicleService, BaseService<Vehicle, Int>() {

    init {
        primaryRepo = vehicleRepository
    }

    override fun getInstanceClass(): KClass<Vehicle> {
        return Vehicle::class
    }

    override fun create(dto: Any): Vehicle {
        dto as VehicleRequest
        val vehicle = assignObject(Vehicle(vehicleCategory = vehicleCategoryRepository.getOne(dto.category_id)), dto)
        return primaryRepo.save(vehicle)
    }

    override fun edit(id: Int, dto: Any): Vehicle {
        dto as VehicleRequest
        val vehicleExist = singleById(id)
        vehicleExist.vehicleCategory = vehicleCategoryRepository.getOne(dto.category_id)
        val vehicle = assignObject(vehicleExist, dto)
        return primaryRepo.save(vehicle)
    }
}