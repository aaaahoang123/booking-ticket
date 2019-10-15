package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.enum.role.ROLE_MANAGER_CUSTOMER_TYPE
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.CustomerTypeRequest
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.service.interfaces.CustomerTypeService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin
@RequestMapping("$API_PREFIX/customer-types")
@Secured(ROLE_MANAGER_CUSTOMER_TYPE)
class CustomerTypeController @Autowired constructor(
        private val customerTypeService: CustomerTypeService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @PostMapping
    fun createCustomerType(@RequestBody @Valid dto: CustomerTypeRequest): ResponseEntity<Any> {
        val customerType = customerTypeService.create(dto)
        val result = dtoBuilder.buildCustomerTypeDto(customerType)
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/{id}"])
    fun getCustomerType(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val customerType = customerTypeService.singleById(id)
        val result = dtoBuilder.buildCustomerTypeDto(customerType)
        return restResponse.restSuccess(result)
    }

    @PutMapping(value = ["/{id}"])
    fun editCustomerType(@PathVariable("id") id: Int, @RequestBody @Valid dto: CustomerTypeRequest): ResponseEntity<Any> {
        val customerType = customerTypeService.edit(id, dto)
        val result = dtoBuilder.buildCustomerTypeDto(customerType)
        return restResponse.restSuccess(result)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteCustomerType(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val customerType = customerTypeService.delete(id)
        val result = dtoBuilder.buildCustomerTypeDto(customerType)
        return restResponse.restSuccess(result)
    }
}