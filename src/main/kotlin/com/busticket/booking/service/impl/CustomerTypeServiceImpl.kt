package com.busticket.booking.service.impl

import com.busticket.booking.entity.CustomerType
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.customer.CustomerTypeRepository
import com.busticket.booking.request.CustomerTypeRequest
import com.busticket.booking.service.interfaces.CustomerTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class CustomerTypeServiceImpl @Autowired constructor(
        private val customerTypeRepository: CustomerTypeRepository
) : CustomerTypeService, BaseService<CustomerType, Int>() {
    override fun getInstanceClass(): KClass<CustomerType> {
        return CustomerType::class
    }

    init {
        primaryRepo = customerTypeRepository
    }

    override fun create(dto: Any): CustomerType {
        dto as CustomerTypeRequest
        val customerType = assignObject(CustomerType(), dto)
        return primaryRepo.save(customerType)
    }

    override fun edit(id: Int, dto: Any): CustomerType {
        dto as CustomerTypeRequest
        val customerExist = customerTypeRepository.findById(id).orElse(null)
        customerExist.name = dto.name
        dto.description?.let { customerExist.description = it }
        customerExist.discount = dto.discount
        return customerTypeRepository.save(customerExist)
    }
}