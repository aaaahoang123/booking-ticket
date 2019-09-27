package com.busticket.booking.service.impl

import com.busticket.booking.entity.Customer
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.customer.CustomerRepository
import com.busticket.booking.request.CustomerRequest
import com.busticket.booking.service.interfaces.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class CustomerServiceImpl @Autowired constructor(
        private val customerRepository: CustomerRepository
) : CustomerService, BaseService<Customer, Int>() {
    override fun getInstanceClass(): KClass<Customer> {
        return Customer::class
    }

    init {
        primaryRepo = customerRepository
    }

    override fun create(dto: Any): Customer {
        dto as CustomerRequest
        val customer = assignObject(Customer(), dto)
        return primaryRepo.save(customer)
    }
}