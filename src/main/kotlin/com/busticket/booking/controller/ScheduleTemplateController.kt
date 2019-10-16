package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.enum.role.ROLE_MANAGER_SCHEDULE_TEMPLATE
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.request.UserRequest
import com.busticket.booking.request.VehicleRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.ScheduleTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.annotation.Secured
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/schedule-templates")
@CrossOrigin
@Secured(ROLE_MANAGER_SCHEDULE_TEMPLATE)
class ScheduleTemplateController @Autowired constructor(
        private val scheduleTemplateService: ScheduleTemplateService,
        private val dtoBuilder: DtoBuilderService,
        private val restResponse: RestResponseService
) {
    @PostMapping
    fun createScheduleTemplate(@RequestBody @Valid dto: ScheduleTemplateRequest): ResponseEntity<Any> {
        val scheduleTemplate = scheduleTemplateService.create(dto)
        val result = dtoBuilder.buildScheduleTemplateDto(scheduleTemplate)
        return restResponse.restSuccess(result)
    }

    @PutMapping(value = ["/{id}"])
    fun editScheduleTemplate(@PathVariable("id") id: Int, @RequestBody @Valid dto: ScheduleTemplateRequest): ResponseEntity<Any> {
        val scheduleTemplate = scheduleTemplateService.edit(id, dto)
        val result = dtoBuilder.buildScheduleTemplateDto(scheduleTemplate)
        return restResponse.restSuccess(result)
    }

    @GetMapping(value = ["/{id}"])
    fun getScheduleTemplateById(@PathVariable("id") id: Int): ResponseEntity<Any> {
        println(id)
        val scheduleTemplate = scheduleTemplateService.singleById(id)
        val result = dtoBuilder.buildScheduleTemplateDto(scheduleTemplate)
        return restResponse.restSuccess(result)
    }

    @DeleteMapping(value = ["/{id}"])
    fun deleteScheduleTemplateById(@PathVariable("id") id: Int): ResponseEntity<Any> {
        val scheduleTemplate = scheduleTemplateService.delete(id)
        val result = dtoBuilder.buildScheduleTemplateDto(scheduleTemplate)
        return restResponse.restSuccess(result)
    }
}