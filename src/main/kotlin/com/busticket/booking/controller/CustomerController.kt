package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.service.interfaces.CustomerService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
@RequestMapping("$API_PREFIX/customers")
class CustomerController @Autowired constructor(
        private val customerService: CustomerService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @GetMapping
    fun listCustomer(): ResponseEntity<Any> {
        val customers = customerService.findAllActiveItems()
        val result = customers.map { dtoBuilder.buildCustomerDto(it) }
        return restResponse.restSuccess(result)
    }
}