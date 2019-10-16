package com.busticket.booking.service.interfaces

import com.busticket.booking.dto.*
import com.busticket.booking.entity.*
import com.busticket.booking.request.ScheduleTemplateRequest

interface DtoBuilderService {
    fun buildUserDto(user: User, token: String? = null): UserDto
    fun buildVoyageDto(voyage: Voyage): VoyageDto
    fun buildVoyagePartDto(voyagePart: VoyagePart): VoyagePartDto
    fun buildVehicleCategoryDto(vehicleCategory: VehicleCategory): VehicleCategoryDto
    fun buildVehicleDto(vehicle: Vehicle): VehicleDto
    fun buildProvinceDto(province: Province): ProvinceDto
    fun buildDistrictDto(district: District): DistrictDto
    fun buildStreetDto(street: Street): StreetDto
    fun buildScheduleTemplateDto(scheduleTemplate: ScheduleTemplate): ScheduleTemplateDto
    fun buildScheduleDto(schedule: Schedule): ScheduleDto
    fun buildCustomerTypeDto(customerType: CustomerType): CustomerTypeDto
    fun buildCustomerDto(customer: Customer): CustomerDto
    fun buildOrderDto(order: Order): OrderDto
    fun buildOrderDetailDto(orderDetail: OrderDetail): OrderDetailDto
    fun buildPolicyDto(policy: UserPolicy): Map<String, Any?>
    fun buildRoleDto(role: UserRole): Map<String, Any?>
}