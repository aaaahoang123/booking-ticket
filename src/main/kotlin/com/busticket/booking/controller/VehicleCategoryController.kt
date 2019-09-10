package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VehicleCategoryRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VehicleCategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/vehicle-categories")
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
}