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
}