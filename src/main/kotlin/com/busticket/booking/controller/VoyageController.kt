package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.dto.StreetDto
import com.busticket.booking.dto.VoyagePartDto
import com.busticket.booking.entity.Street
import com.busticket.booking.entity.VoyagePart
import com.busticket.booking.enum.role.ROLE_MANAGER_VOYAGE
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.VoyageRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.VoyageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
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
    @Secured(ROLE_MANAGER_VOYAGE)
    fun createVoyage(@RequestBody @Valid dto: VoyageRequest): ResponseEntity<Any> {
        val voyage = voyageService.create(dto)
        val result = dtoBuilder.buildVoyageDto(voyage)
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/{id}"])
    fun getVoyage(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val voyage = voyageService.getById(id).get()
        val result = dtoBuilder.buildVoyageDto(voyage)
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/{id}/part"])
    fun getPartFromTo(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val voyage = voyageService.getById(id).get()
        val partsVoyageFrom = mutableListOf<StreetDto>()
        val partVoyageTo = mutableListOf<StreetDto>()
        voyage.voyageParts?.forEach {
            partsVoyageFrom.add(dtoBuilder.buildStreetDto(it.from!!))
            partVoyageTo.add(dtoBuilder.buildStreetDto(it.to!!))
        }
        return restResponse.restSuccess(mapOf(
                "partsVoyageFrom" to partsVoyageFrom,
                "partVoyageTo" to partVoyageTo
        ))
    }
}
