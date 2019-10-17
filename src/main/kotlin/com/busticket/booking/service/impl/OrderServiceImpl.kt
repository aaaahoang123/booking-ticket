package com.busticket.booking.service.impl

import com.busticket.booking.dto.*
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
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import kotlin.reflect.KClass

@Service
class OrderServiceImpl @Autowired constructor(
        private val orderRepo: OrderRepository,
        private val customerRepo: CustomerRepository,
        private val customerService: CustomerService,
        private val scheduleRepo: ScheduleRepository,
        private val vehicleCategoryRepo: VehicleCategoryRepository,
        private val customerTypeRepo: CustomerTypeRepository,
        private val voyagePartRepo: VoyagePartRepository,
        private val dtoBuilder: DtoBuilderService

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

            assignObject(
                    OrderDetail(
                            travelFrom = travelFrom,
                            travelTo = travelTo,
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

    fun orderStatistics(orders: List<Order>): List<OrderStatisticDto> {
        val orderStatistic = mutableListOf<OrderStatisticDto>()
        var countPaid = 0
        var totalRevenuePaid = 0.0
        var totalRevenueUnPaid = 0.0
        var countUnPaid = 0
        orders.forEach() {
            if (it.status == 1) {
                countPaid += 1
                totalRevenuePaid += it.finalPrice!!
            } else if (it.status == -1) {
                countUnPaid += 1
                totalRevenueUnPaid += it.finalPrice!!
            }
        }
        orderStatistic.add(OrderStatisticDto(
                total = countPaid,
                status = 1,
                totalRevenue = totalRevenuePaid
        ))
        orderStatistic.add(OrderStatisticDto(
                total = countUnPaid,
                status = -1,
                totalRevenue = totalRevenueUnPaid
        ))
        return orderStatistic
    }


    fun getOrderByFromAndTo(from: Long, to: Long): List<Order> {
        var spec = Specification.where(initSpec<Order>())
        spec = from.let { spec.and(statisticFrom(it)) }
        spec = to.let { spec.and(statisticTo(it)) }
        return orderRepo.findAll(spec)
    }


    fun revenueStatistics(orders: List<Order>, from: Long, to: Long): List<Any> {
        return orderRepo.revenueStatistics(from, to)
    }

    fun customerRanking(): List<CustomerRankingDto> {
        val customers = customerRepo.getAllAndJoin()
        val customerRanking = mutableListOf<CustomerRankingDto>()
        customers.map {
            customerRanking.add(CustomerRankingDto(
                    customerId = it.id,
                    orderQuantity = it.orders!!.size,
                    customer = dtoBuilder.buildCustomerDto(it)
            ))
        }

        return customerRanking.sortedWith(compareByDescending {
            it.orderQuantity
        })
    }

    override fun statistics(from: Long?, to: Long?): StatisticDto {
        val startDayOfMonth = Calendar.getInstance()
        startDayOfMonth.set(Calendar.DAY_OF_MONTH, 1)
        startDayOfMonth.set(Calendar.HOUR_OF_DAY, 0)
        startDayOfMonth.set(Calendar.MINUTE, 0)
        startDayOfMonth.set(Calendar.SECOND, 0)
        val realFrom = from ?: startDayOfMonth.timeInMillis
        val currentDay = Calendar.getInstance()
        val realTo = to ?: currentDay.timeInMillis


        val orders = getOrderByFromAndTo(realFrom, realTo)
        val orderStatistic = orderStatistics(orders)
        val revenueStatistics = revenueStatistics(orders, realFrom, realTo)
        val customerRanking = customerRanking()
        return StatisticDto(
                orderStatistics = orderStatistic,
                revenueStatistics = revenueStatistics
//                customerRanking = customerRanking
        )
    }

}