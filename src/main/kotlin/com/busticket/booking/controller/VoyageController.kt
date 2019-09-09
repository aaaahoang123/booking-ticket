package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VoyageRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VoyageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/voyages")
class VoyageController @Autowired constructor(
        private val voyageService: VoyageService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @PostMapping("/create")
    fun createVoyage(@RequestBody @Valid dto: VoyageRequest): ResponseEntity<Any> {
        val voyage = voyageService.create(dto)
        val result = dtoBuilder.buildVoyageDto(voyage)
        return restResponse.restSuccess(result)
    }
}