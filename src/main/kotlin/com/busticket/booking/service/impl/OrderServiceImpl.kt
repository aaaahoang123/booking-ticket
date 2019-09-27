package com.busticket.booking.service.impl

import com.busticket.booking.dto.CustomerDto
import com.busticket.booking.entity.Customer
import com.busticket.booking.entity.Order
import com.busticket.booking.entity.OrderDetail
import com.busticket.booking.entity.User
import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.customer.CustomerRepository
import com.busticket.booking.repository.customer.CustomerTypeRepository
import com.busticket.booking.repository.order.OrderRepository
import com.busticket.booking.repository.schedule.ScheduleRepository
import com.busticket.booking.repository.vehicle.VehicleCategoryRepository
import com.busticket.booking.repository.voyage.VoyagePartRepository
import com.busticket.booking.request.CustomerRequest
import com.busticket.booking.request.OrderRequest
import com.busticket.booking.service.interfaces.CustomerService
import com.busticket.booking.service.interfaces.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class OrderServiceImpl @Autowired constructor(
        private val orderRepo: OrderRepository,
        private val customerRepo: CustomerRepository,
        private val customerService: CustomerService,
        private val scheduleRepo: ScheduleRepository,
        private val vehicleCategoryRepo: VehicleCategoryRepository,
        private val customerTypeRepo: CustomerTypeRepository,
        private val voyagePartRepo: VoyagePartRepository

) : OrderService, BaseService<Order, Int>() {

    init {
        primaryRepo = orderRepo
    }

    override fun getInstanceClass(): KClass<Order> {
        return Order::class
    }

    override fun create(creator: User, dto: OrderRequest): Order {
        val order = Order()
        val customerExist = customerRepo.findByPhoneNumber(dto.phoneNumber)
        if (customerExist.isPresent) {
            order.customer = customerExist.get()
        } else {
            order.customer = customerService.create(CustomerRequest(
                    name = dto.customerName,
                    phoneNumber = dto.phoneNumber
            ))
        }

        var finalPrice: Double = 0.0
        val schedule = scheduleRepo.findById(dto.scheduleId).orElse(null)
        val orderDetails = dto.orderDetailRequest.map {
            val vehicleCategory = vehicleCategoryRepo.findById(it.vehicleCategoryId).get()
            val customerType = customerTypeRepo.findById(it.customerTypeId).get()
            val travelFrom = voyagePartRepo.findById(it.travelFromId).get()
            val travelTo = voyagePartRepo.findById(it.travelToId).get()
            val voyagePart = voyagePartRepo.findByOrderNumberBetween(schedule.voyage!!, travelFrom.orderNumber, travelTo.orderNumber)
            var distance = 0
            voyagePart.map { part -> distance += part.distance }
            val discount = customerType.discount
            val originTotalPrice: Double = (distance * vehicleCategory.price * it.quantity).toDouble()

            val moneyIsReduced: Double = ((originTotalPrice * discount) / 100.00)
            val totalPrice = originTotalPrice - moneyIsReduced
            finalPrice += totalPrice
            println("distance: $distance")
            assignObject(
                    OrderDetail(
                            order = order,
                            vehicleCategory = vehicleCategory,
                            customerType = customerType,
                            unitPrice = vehicleCategory.price,
                            discount = discount,
                            originToTalPrice = originTotalPrice,
                            totalPrice = totalPrice
                    ),
                    it
            )

        }.toSet()
        order.schedule = schedule
        order.orderDetail = orderDetails
        order.createdBy = creator
        order.paidStatus = dto.paidStatus
        order.finalPrice = finalPrice
        return orderRepo.save(order)
    }

    override fun delete(id: Int): Order {
        val order = orderRepo.findById(id).get()
        order.orderDetail.map { it.status = CommonStatus.INACTIVE.value }
        order.status = CommonStatus.INACTIVE.value
        return orderRepo.save(order)
    }
}