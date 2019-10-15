package com.busticket.booking.service.impl

import com.busticket.booking.CLIENT_DATE_FORMAT
import com.busticket.booking.dto.*
import com.busticket.booking.entity.*
import com.busticket.booking.enum.status.commonStatusTitle
import com.busticket.booking.lib.datetime.format
import com.busticket.booking.lib.locale.LocaleService
import com.busticket.booking.service.interfaces.DtoBuilderService
import org.hibernate.Hibernate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
@Scope("request", proxyMode = ScopedProxyMode.TARGET_CLASS)
class DtoBuilderServiceImpl @Autowired constructor(
        private val localeService: LocaleService,
        private val req: HttpServletRequest
) : DtoBuilderService {
    override fun buildUserDto(user: User, token: String?): UserDto {
        return UserDto(
                id = user.id!!,
                email = user.email,
                name = user.name,
                address = user.address,
                avatar = user.avatar,
                birthday = user.birthday,
                gender = user.gender,
                phoneNumber = user.phoneNumber,
                status = user.status,
                statusTitle = localeService.getMessage(commonStatusTitle(user.status), req),
                createdAt = user.createdAt,
                updatedAt = user.updatedAt,
                createdAtStr = format(user.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(user.updatedAt, CLIENT_DATE_FORMAT),
                accessToken = token
        )
    }

    override fun buildVoyageDto(voyage: Voyage): VoyageDto {
        val result = VoyageDto(
                id = voyage.id!!,
                name = voyage.name,
                createdAt = voyage.createdAt,
                updatedAt = voyage.updatedAt,
                status = voyage.status
        )
        if (Hibernate.isInitialized(voyage.voyageParts))
            result.voyageParts = voyage.voyageParts!!.map { buildVoyagePartDto(it) }
        if (Hibernate.isInitialized(voyage.scheduleTemplates)) {
            result.scheduleTemplates = voyage.scheduleTemplates.map { buildScheduleTemplateDto(it) }
        }
        return result
    }

    override fun buildVoyagePartDto(voyagePart: VoyagePart): VoyagePartDto {
        return VoyagePartDto(
                id = voyagePart.id!!,
                fromId = voyagePart.from?.id ?: 0,
                fromName = voyagePart.from?.name ?: "",
                toId = voyagePart.to?.id ?: 0,
                toName = voyagePart.to?.name ?: "",
                distance = voyagePart.distance,
                orderNumber = voyagePart.orderNumber,
                createdAt = voyagePart.createdAt,
                updatedAt = voyagePart.updatedAt,
                createdAtStr = format(voyagePart.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(voyagePart.updatedAt, CLIENT_DATE_FORMAT),
                status = voyagePart.status

        )
    }

    override fun buildVehicleCategoryDto(vehicleCategory: VehicleCategory): VehicleCategoryDto {
        val result = VehicleCategoryDto(
                id = vehicleCategory.id!!,
                name = vehicleCategory.name,
                seatQuantity = vehicleCategory.seatQuantity,
                price = vehicleCategory.price,
                createdAt = vehicleCategory.createdAt,
                updatedAt = vehicleCategory.updatedAt,
                createdAtStr = format(vehicleCategory.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(vehicleCategory.updatedAt, CLIENT_DATE_FORMAT),
                status = vehicleCategory.status
        )
        if (Hibernate.isInitialized(vehicleCategory.vehicles))
            result.vehicles = vehicleCategory.vehicles.map { buildVehicleDto(it) }

        return result
    }

    override fun buildVehicleDto(vehicle: Vehicle): VehicleDto {
        return VehicleDto(
                id = vehicle.id!!,
                name = vehicle.name,
                plate = vehicle.plate,
                color = vehicle.color,
                category_id = vehicle.vehicleCategoryId!!,
                createdAt = vehicle.createdAt,
                updatedAt = vehicle.updatedAt,
                createdAtStr = format(vehicle.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(vehicle.updatedAt, CLIENT_DATE_FORMAT),
                status = vehicle.status
        )
    }

    override fun buildProvinceDto(province: Province): ProvinceDto {
        return ProvinceDto(
                id = province.id!!,
                name = province.name,
                code = province.code
        )
    }

    override fun buildDistrictDto(district: District): DistrictDto {
        return DistrictDto(
                id = district.id!!,
                name = district.name,
                prefix = district.prefix
        )
    }

    override fun buildStreetDto(street: Street): StreetDto {
        return StreetDto(
                id = street.id!!,
                name = street.name,
                prefix = street.prefix,
                tag = street.tag
        )
    }

    override fun buildScheduleTemplateDto(scheduleTemplate: ScheduleTemplate): ScheduleTemplateDto {
        val result = ScheduleTemplateDto(
                id = scheduleTemplate.id!!,
                timeStart = scheduleTemplate.timeStart!!,
                timeEnd = scheduleTemplate.timeEnd!!,
                createdAt = scheduleTemplate.createdAt,
                updatedAt = scheduleTemplate.updatedAt,
                createdAtStr = format(scheduleTemplate.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(scheduleTemplate.updatedAt, CLIENT_DATE_FORMAT),
                status = scheduleTemplate.status


        )
        if (Hibernate.isInitialized(scheduleTemplate.voyages))
            result.voyages = scheduleTemplate.voyages.map { buildVoyageDto(it) }.toSet()
        return result
    }

    override fun buildScheduleDto(schedule: Schedule): ScheduleDto {
        val result = ScheduleDto(
                id = schedule.id!!,
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                scheduleTemplateId = schedule.scheduleTemplateId,
                vehicleCategoryId = schedule.vehicleCategoryId,
                vehicleId = schedule.vehicleId,
                voyageId = schedule.voyageId,
                driverId = schedule.driverId,
                createdById = schedule.createdById,
                createdAt = schedule.createdAt,
                updatedAt = schedule.updatedAt,
                createdAtStr = format(schedule.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(schedule.updatedAt, CLIENT_DATE_FORMAT),
                status = schedule.status
        )
        if (Hibernate.isInitialized(schedule.scheduleTemplate)) {
            result.scheduleTemplate = buildScheduleTemplateDto(schedule.scheduleTemplate!!)
        }
        if (Hibernate.isInitialized(schedule.vehicleCategory)) {
            result.vehicleCategory = schedule.vehicleCategory?.let { buildVehicleCategoryDto(it) }
        }
        if (Hibernate.isInitialized(schedule.voyage)) {
            result.voyage = buildVoyageDto(schedule.voyage!!)
        }
        if (Hibernate.isInitialized(schedule.vehicle)) {
            result.vehicle = schedule.vehicle?.let { buildVehicleDto(it) }
        }
        if (Hibernate.isInitialized(schedule.driver)) {
            result.driver = schedule.driver?.let { buildUserDto(it) }
        }
        return result
    }

    override fun buildCustomerTypeDto(customerType: CustomerType): CustomerTypeDto {
        return CustomerTypeDto(
                id = customerType.id!!,
                name = customerType.name,
                description = customerType.description,
                discount = customerType.discount,
                createdAt = customerType.createdAt,
                updatedAt = customerType.updatedAt,
                createdAtStr = format(customerType.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(customerType.updatedAt, CLIENT_DATE_FORMAT),
                status = customerType.status
        )
    }

    override fun buildCustomerDto(customer: Customer): CustomerDto {
        return CustomerDto(
                id = customer.id!!,
                name = customer.name,
                phoneNumber = customer.phoneNumber,
                createdAt = customer.createdAt,
                updatedAt = customer.updatedAt,
                createdAtStr = format(customer.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(customer.updatedAt, CLIENT_DATE_FORMAT),
                status = customer.status
        )
    }

    override fun buildOrderDetailDto(orderDetail: OrderDetail): OrderDetailDto {
        val result = OrderDetailDto(
                orderId = orderDetail.orderId,
                vehicleCategoryId = orderDetail.vehicleCategoryId,
                customerTypeId = orderDetail.customerTypeId!!,
                unitPrice = orderDetail.unitPrice!!,
                originTotalPrice = orderDetail.originToTalPrice!!,
                totalPrice = orderDetail.totalPrice!!,
                quantity = orderDetail.quantity!!,
                discount = orderDetail.discount!!,
                travelFromId = orderDetail.travelFromId,
                travelToId = orderDetail.travelToId,
                createdAt = orderDetail.createdAt,
                updatedAt = orderDetail.updatedAt,
                createdAtStr = format(orderDetail.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(orderDetail.updatedAt, CLIENT_DATE_FORMAT),
                status = orderDetail.status
        )
        if (Hibernate.isInitialized(orderDetail.travelFrom)) {
            result.travelFrom = buildVoyagePartDto(orderDetail.travelFrom!!)
        }
        if (Hibernate.isInitialized(orderDetail.travelTo)) {
            result.travelTo = buildVoyagePartDto(orderDetail.travelTo!!)
        }
        return result
    }

    override fun buildOrderDto(order: Order): OrderDto {
        val result =  OrderDto(
                id = order.id!!,
                customerId = order.customerId,
                scheduleId = order.scheduleId,
                createdById = order.createdById,
                finalPrice = order.finalPrice!!,
                paidStatus = order.paidStatus!!,
                orderDetails = order.orderDetail.map { buildOrderDetailDto(it) },
                createdAt = order.createdAt,
                updatedAt = order.updatedAt,
                createdAtStr = format(order.createdAt, CLIENT_DATE_FORMAT),
                updatedAtStr = format(order.updatedAt, CLIENT_DATE_FORMAT),
                status = order.status
        )
        if(Hibernate.isInitialized(order.customer)) {
            result.customer = buildCustomerDto(order.customer!!)
        }
        if (Hibernate.isInitialized(order.createdBy)) {
            result.createdBy = buildUserDto(order.createdBy!!)
        }
        if (Hibernate.isInitialized(order.schedule)) {
            result.schedule = buildScheduleDto(order.schedule!!)
        }
        return result
    }
}