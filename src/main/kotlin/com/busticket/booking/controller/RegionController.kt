package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.entity.User
import com.busticket.booking.lib.auth.ReqUser
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.service.interfaces.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_PREFIX/region")
@CrossOrigin
class RegionController @Autowired constructor(
        private val regionService: RegionService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {

    @GetMapping(value = ["/provinces"])
    fun getAllProvince(): ResponseEntity<Any> {
        val province = regionService.getAllProvince()
        val result = province.map { dtoBuilder.buildProvinceDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/districts"])
    fun getAllDistrictByProvinceId(@RequestParam provinceId: Int): ResponseEntity<Any> {
        val district = regionService.getAllDistrictByProvinceId(provinceId)
        val result = district.map { dtoBuilder.buildDistrictDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/streets"])
    fun getAllStreetByDistrictId(@RequestParam districtId: Int): ResponseEntity<Any> {
        val street = regionService.getAllStreetByDistrictId(districtId)
        val result = street.map { dtoBuilder.buildStreetDto(it) }
        return restResponse.restSuccess(result)
    }
}