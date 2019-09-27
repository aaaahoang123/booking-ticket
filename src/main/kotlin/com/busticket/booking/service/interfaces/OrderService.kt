package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.Order
import com.busticket.booking.entity.User
import com.busticket.booking.request.OrderRequest

interface OrderService : IBaseService<Order, Int> {
    fun create(creator: User, dto: OrderRequest): Order
}