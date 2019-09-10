package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VehicleRequest
import com.busticket.booking.request.VoyageRequest
import com.busticket.booking.service.impl.VehicleServiceImpl
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VehicleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/vehicles")
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

}