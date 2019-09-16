package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VoyageRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VoyageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/voyages")
@CrossOrigin
class VoyageController @Autowired constructor(
        private val voyageService: VoyageService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @PostMapping
    fun createVoyage(@RequestBody @Valid dto: VoyageRequest): ResponseEntity<Any> {
        val voyage = voyageService.create(dto)
        val result = dtoBuilder.buildVoyageDto(voyage)
        return restResponse.restSuccess(result)
    }

    @GetMapping
    fun listVoyage(): ResponseEntity<Any> {
        val listVoyage = voyageService.findAllActiveItems()
        val result = listVoyage.map {
            dtoBuilder.buildVoyageDto(it) }
        return restResponse.restSuccess(result)
    }
}