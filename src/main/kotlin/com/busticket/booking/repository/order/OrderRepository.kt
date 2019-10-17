package com.busticket.booking.repository.order

import com.busticket.booking.dto.RevenueDto
import com.busticket.booking.entity.Order
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : BaseRepository<Order, Int> {
    @Query("select new Date(Order.createdAt) as day, sum(Order.finalPrice) as totalPrice from Order where Order.createdAt >= :start and Order.createdAt <= :to group by day")
    fun revenueStatistics(@Param("start") start: Long, @Param("to") to: Long): List<RevenueDto>
}