package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.entity.User
import com.busticket.booking.lib.auth.ReqUser
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.service.interfaces.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(value = [API_PREFIX])
@CrossOrigin
class NotCheckPolicyController @Autowired constructor(
        private val voyageService: VoyageService,
        private val vehicleService: VehicleService,
        private val vehicleCategoryService: VehicleCategoryService,
        private val scheduleTemplateService: ScheduleTemplateService,
        private val customerTypeService: CustomerTypeService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService,
        private val scheduleService: ScheduleService,
        private val userService: UserService
        ) {
    @GetMapping(value = ["/customer-types"])
    fun listCustomerType(): ResponseEntity<Any> {
        val customerTypes = customerTypeService.findAllActiveItems()
        val result = customerTypes.map { dtoBuilder.buildCustomerTypeDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/users/user-data"])
    fun userData(@ReqUser user: User, req: HttpServletRequest): ResponseEntity<Any> {
        return restResponse.restSuccess(dtoBuilder.buildUserDto(user, req.getHeader(AUTHORIZATION) as String))
    }

    @GetMapping("/schedules")
    fun listSchedule(): ResponseEntity<Any> {
        val schedules = scheduleService.findAllActiveItems()
        val result = schedules.map { dtoBuilder.buildScheduleDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping("/schedule-templates")
    fun listScheduleTemplate(): ResponseEntity<Any> {
        val listScheduleTemplate = scheduleTemplateService.findAllActiveItems()
        val result = listScheduleTemplate.map { dtoBuilder.buildScheduleTemplateDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/users/list"])
    fun listUsers(): ResponseEntity<Any> {
        return restResponse.restSuccess(userService.findAllActiveItems().map { u -> dtoBuilder.buildUserDto(u) })
    }

    @GetMapping("/vehicle-categories")
    fun getAllVehicleCategory(): ResponseEntity<Any> {
        val listVehicleCategory = vehicleCategoryService.findAllActiveItems()
        val result = listVehicleCategory.map { dtoBuilder.buildVehicleCategoryDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping("/vehicles")
    fun getAllVehicle(): ResponseEntity<Any> {
        val listVehicle = vehicleService.findAllActiveItems()
        val result = listVehicle.map { dtoBuilder.buildVehicleDto(it) }
        return restResponse.restSuccess(result)
    }

    @GetMapping("/voyages")
    fun listVoyage(): ResponseEntity<Any> {
        val listVoyage = voyageService.findAllActiveItems()
        val result = listVoyage.map {
            dtoBuilder.buildVoyageDto(it)
        }
        return restResponse.restSuccess(result)
    }
}
