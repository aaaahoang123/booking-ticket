package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.dto.PaginationDto
import com.busticket.booking.entity.Customer
import com.busticket.booking.entity.Order
import com.busticket.booking.entity.User
import com.busticket.booking.enum.role.ROLE_MANAGER_ORDER
import com.busticket.booking.lib.auth.ReqUser
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.OrderRequest
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.OrderService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@CrossOrigin
@RequestMapping("$API_PREFIX/orders")
@Secured(ROLE_MANAGER_ORDER)
class OrderController @Autowired constructor(
        private val orderService: OrderService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
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

    @GetMapping(value = ["/{id}"])
    fun getOrder(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val order = orderService.singleById(id)
        val result = dtoBuilder.buildOrderDto(order)
        return restResponse.restSuccess(result)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteOrder(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val order = orderService.delete(id)
        val result = dtoBuilder.buildOrderDto(order)
        return restResponse.restSuccess(result)
    }

    @PostMapping("/calculate")
    fun orderCalculate(@RequestBody @Valid dto: OrderRequest, @ReqUser user: User): ResponseEntity<Any> {
        val order = orderService.buildOderDetails(dto)
        return restResponse.restSuccess(mapOf(
                "totalPrice" to orderService.calculateFinalPrice(order)
        ))
    }

    @GetMapping("/search")
    fun search(@RequestParam("customerId") customerId: Int? = null,
               @RequestParam("createdById") createdById: Int? = null,
               @RequestParam("scheduleId") scheduleId: Int? = null,
               @RequestParam("paidStatus") paidStatus: Int? = null,
               @RequestParam("status") status: Int? = null,
               @RequestParam("page") page: Int? = null,
               @RequestParam("limit") limit: Int? = null
               ): ResponseEntity<Any> {

        val realPage = page ?: 0
        val realLimit = limit ?: 20
        val orders = orderService.search(customerId, createdById, scheduleId, paidStatus, status, realPage, realLimit)
        val result = orders.content.map { dtoBuilder.buildOrderDto(it) }
        val pagination = PaginationDto(orders, realPage, realLimit)
        return restResponse.restSuccess(result, pagination)
    }
}