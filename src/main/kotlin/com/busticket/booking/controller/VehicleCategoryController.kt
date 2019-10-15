package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.enum.role.ROLE_MANAGER_VEHICLE_CATEGORY
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VehicleCategoryRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VehicleCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/vehicle-categories")
@CrossOrigin
@Secured(ROLE_MANAGER_VEHICLE_CATEGORY)
class VehicleCategoryController @Autowired constructor(
        private val vehicleCategoryService: VehicleCategoryService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {

    @PostMapping
    fun createVehicleCategory(@RequestBody @Valid dto: VehicleCategoryRequest): ResponseEntity<Any> {
        val vehicle = vehicleCategoryService.create(dto)
        val result = dtoBuilder.buildVehicleCategoryDto(vehicle)
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/{id}"])
    fun getVehicleCategoryById(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val vehicleCategory = vehicleCategoryService.singleById(id)
        val result = dtoBuilder.buildVehicleCategoryDto(vehicleCategory)
        return restResponse.restSuccess(result)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteVehicleCategory(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val vehicleCategory = vehicleCategoryService.delete(id)
        val result = dtoBuilder.buildVehicleCategoryDto(vehicleCategory)
        return restResponse.restSuccess(result)
    }

    @PutMapping(value = ["/{id}"])
    fun editVehicleCategory(@PathVariable("id") id: Int, @RequestBody @Valid dto: VehicleCategoryRequest): ResponseEntity<Any> {
        val vehicleCategory = vehicleCategoryService.edit(id, dto)
        val result = dtoBuilder.buildVehicleCategoryDto(vehicleCategory)
        return restResponse.restSuccess(result)
    }

}