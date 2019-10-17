package com.busticket.booking.repository.order

import com.busticket.booking.dto.RevenueDto
import com.busticket.booking.entity.Order
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface OrderRepository : BaseRepository<Order, Int> {
    @Query("select date(FROM_UNIXTIME(ord.createdAt/1000)) as day, sum(ord.finalPrice) as totalPrice from Order ord where ord.createdAt >= :start and ord.createdAt <= :to group by date(FROM_UNIXTIME(ord.createdAt/1000))")
    fun revenueStatistics(@Param("start") start: Long, @Param("to") to: Long): List<Any>
}