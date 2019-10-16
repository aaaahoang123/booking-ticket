package com.busticket.booking.service.interfaces

import com.busticket.booking.dto.OrderStatisticDto
import com.busticket.booking.dto.RevenueStatisticDto
import com.busticket.booking.dto.StatisticDto
import com.busticket.booking.entity.Order
import com.busticket.booking.entity.OrderDetail
import com.busticket.booking.entity.User
import com.busticket.booking.request.OrderRequest
import org.springframework.data.domain.Page

interface OrderService : IBaseService<Order, Int> {
    fun create(creator: User, dto: OrderRequest): Order
    fun search(customerId: Int? = null,
               createdById: Int? = null,
               scheduleId: Int? = null,
               paidStatus: Int? = null,
               status: Int? = null,
               page: Int = 0,
               limit: Int = 20
    ): Page<Order>

    fun buildOderDetails(dto: OrderRequest): List<OrderDetail>
    fun calculateFinalPrice(orderDetails: List<OrderDetail>, order: Order? = null): Double

    fun statistics(from: Long? = null,
                   to: Long? = null): StatisticDto
//    fun revenueStatistics(from: Long? = null,
//                          to: Long? = null): List<RevenueStatisticDto>
}