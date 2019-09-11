package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VehicleCategoryRequest
import com.busticket.booking.request.VehicleRequest
import com.busticket.booking.request.VoyageRequest
import com.busticket.booking.service.impl.VehicleServiceImpl
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VehicleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/vehicles")
@CrossOrigin
class VehicleController @Autowired constructor(
        private val vehicleService: VehicleService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @PostMapping
    fun createVehicle(@RequestBody @Valid dto: VehicleRequest): ResponseEntity<Any> {
        val vehicle = vehicleService.create(dto)
        val result = dtoBuilder.buildVehicleDto(vehicle)
        return restResponse.restSuccess(result)
    }

    @GetMapping
    fun getAllVehicle(): ResponseEntity<Any> {
        val listVehicle = vehicleService.findAllActiveItems()
        val result = listVehicle.map { dtoBuilder.buildVehicleDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/{id}"])
    fun getVehicleById(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val vehicle = vehicleService.singleById(id)
        val result = dtoBuilder.buildVehicleDto(vehicle)
        return restResponse.restSuccess(result)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteVehicle(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val vehicle = vehicleService.delete(id)
        val result = dtoBuilder.buildVehicleDto(vehicle)
        return restResponse.restSuccess(result)
    }

    @PutMapping(value = ["/{id}"])
    fun editVehicleCategory(@PathVariable("id") id: Int, @RequestBody @Valid dto: VehicleRequest): ResponseEntity<Any> {
        val vehicle = vehicleService.edit(id, dto)
        val result = dtoBuilder.buildVehicleDto(vehicle)
        return restResponse.restSuccess(result)
    }
}