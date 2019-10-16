package com.busticket.booking.repository.customer

import com.busticket.booking.entity.Customer
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CustomerRepository : BaseRepository<Customer, Int> {
    @Query("select c from Customer c where c.phoneNumber = :phoneNumber and c.status = 1")
    fun findByPhoneNumber(@Param("phoneNumber") phoneNumber: String): Optional<Customer>

    @Query("select distinct c from Customer c inner join c.orders")
    fun getAllAndJoin(): List<Customer>
}