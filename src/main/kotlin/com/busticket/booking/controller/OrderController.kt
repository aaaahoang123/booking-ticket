package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.entity.Customer
import com.busticket.booking.entity.Order
import com.busticket.booking.entity.User
import com.busticket.booking.lib.auth.ReqUser
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.OrderRequest
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.OrderService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin
@RequestMapping("$API_PREFIX/orders")
class OrderController @Autowired constructor(
        private val orderService: OrderService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    //    @PostMapping
//    fun createOrder(@RequestBody @Valid dto: OrderRequest, @ReqUser user: User): ResponseEntity<Any> {
//        val order = orderService.create(user, dto)
//        val result = dtoBuilder.buildOrderDto(order)
//        return restResponse.restSuccess(result)
//    }
    @PostMapping
    fun createOrder(@RequestBody @Valid dto: OrderRequest, @ReqUser user: User): ResponseEntity<Any> {
        val order = orderService.create(user, dto)
        val result = dtoBuilder.buildOrderDto(order)
        return restResponse.restSuccess(result)
    }

    @GetMapping
    fun listOrder(): ResponseEntity<Any> {
        val orders = orderService.findAllActiveItems()
        val result = orders.map { dtoBuilder.buildOrderDto(it) }
        return restResponse.restSuccess(result)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteOrder(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val order = orderService.delete(id)
        val result = dtoBuilder.buildOrderDto(order)
        return restResponse.restSuccess(result)
    }
}