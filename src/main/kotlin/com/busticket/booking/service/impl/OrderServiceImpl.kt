package com.busticket.booking.service.impl

import com.busticket.booking.dto.CustomerDto
import com.busticket.booking.entity.*
import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.customer.CustomerRepository
import com.busticket.booking.repository.customer.CustomerTypeRepository
import com.busticket.booking.repository.initSpec
import com.busticket.booking.repository.order.*
import com.busticket.booking.repository.schedule.ScheduleRepository
import com.busticket.booking.repository.vehicle.VehicleCategoryRepository
import com.busticket.booking.repository.voyage.VoyagePartRepository
import com.busticket.booking.request.CustomerRequest
import com.busticket.booking.request.OrderRequest
import com.busticket.booking.service.interfaces.CustomerService
import com.busticket.booking.service.interfaces.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
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

        val schedule = scheduleRepo.findById(dto.scheduleId).orElse(null)
        val orderDetails = buildOderDetails(dto)
//        val orderDetails = dto.orderDetailRequest.map {
//            val vehicleCategory = vehicleCategoryRepo.findById(it.vehicleCategoryId).get()
//            val customerType = customerTypeRepo.findById(it.customerTypeId).get()
//            val travelFrom = voyagePartRepo.findById(it.travelFromId).get()
//            val travelTo = voyagePartRepo.findById(it.travelToId).get()
//            val voyagePart = voyagePartRepo.findByOrderNumberBetween(schedule.voyage!!, travelFrom.orderNumber, travelTo.orderNumber)
//            var distance = 0
//            voyagePart.map { part -> distance += part.distance }
//            val discount = customerType.discount
//            val originTotalPrice: Double = (distance * vehicleCategory.price * it.quantity).toDouble()
//
//            val moneyIsReduced: Double = ((originTotalPrice * discount) / 100.00)
//            val totalPrice = originTotalPrice - moneyIsReduced
//            finalPrice += totalPrice
//            println("distance: $distance")
//            assignObject(
//                    OrderDetail(
//                            order = order,
//                            vehicleCategory = vehicleCategory,
//                            customerType = customerType,
//                            unitPrice = vehicleCategory.price,
//                            discount = discount,
//                            originToTalPrice = originTotalPrice,
//                            totalPrice = totalPrice
//                    ),
//                    it
//            )
//
//        }.toSet()
        order.schedule = schedule
        order.finalPrice = calculateFinalPrice(orderDetails, order)
        order.orderDetail = orderDetails.toSet()
        order.createdBy = creator
        order.paidStatus = dto.paidStatus
        return orderRepo.save(order)
    }

    override fun delete(id: Int): Order {
        val order = orderRepo.findById(id).get()
        order.orderDetail.map { it.status = CommonStatus.INACTIVE.value }
        order.status = CommonStatus.INACTIVE.value
        return orderRepo.save(order)
    }


    override fun buildOderDetails(dto: OrderRequest): List<OrderDetail> {
        val schedule = scheduleRepo.findById(dto.scheduleId).get()
        return dto.orderDetailRequest.map {
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

            println("distance: $distance")
            assignObject(
                    OrderDetail(
                            vehicleCategory = vehicleCategory,
                            customerType = customerType,
                            unitPrice = vehicleCategory.price,
                            discount = discount,
                            originToTalPrice = originTotalPrice,
                            totalPrice = totalPrice
                    ),
                    it
            )

        }
    }

    override fun calculateFinalPrice(orderDetails: List<OrderDetail>, order: Order?): Double {
        var finalPrice = 0.0
        orderDetails.forEach {
            finalPrice += it.totalPrice!!
            it.order = order
        }
        return finalPrice
    }

    override fun search(customerId: Int?, createdById: Int?, scheduleId: Int?, paidStatus: Int?, status: Int?, page: Int, limit: Int): Page<Order> {
        var spec = Specification.where(initSpec<Order>())
        spec = customerId?.let { spec.and(orderByCustomer(it)) } ?: spec
        spec = createdById?.let { spec.and(orderCreatedBy(it)) } ?: spec
        spec = scheduleId?.let { spec.and(orderOfSchedule(it)) } ?: spec
        spec = paidStatus?.let { spec.and(paidStatus(it)) } ?: spec
        spec = status?.let { spec.and(orderStatus(it)) } ?: spec

        return orderRepo.findAll(spec, PageRequest.of(page, limit))
    }
}