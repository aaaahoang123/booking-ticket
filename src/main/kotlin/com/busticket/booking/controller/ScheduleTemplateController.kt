package com.busticket.booking.controller

import com.busticket.booking.API_PREFIX
import com.busticket.booking.lib.rest.RestResponseService
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.request.UserRequest
import com.busticket.booking.request.VehicleRequest
import com.busticket.booking.service.interfaces.DtoBuilderService
import com.busticket.booking.service.interfaces.ScheduleTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("$API_PREFIX/schedule-templates")
@CrossOrigin
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

    @GetMapping
    fun listScheduleTemplate(): ResponseEntity<Any> {
        val listScheduleTemplate = scheduleTemplateService.findAllActiveItems()
        val result = listScheduleTemplate.map { dtoBuilder.buildScheduleTemplateDto(it) }
        return restResponse.restSuccess(result)
    }

    @PutMapping(value = ["/{id}"])
    fun editScheduleTemplate(@PathVariable("id") id: Int, @RequestBody @Valid dto: ScheduleTemplateRequest): ResponseEntity<Any> {
        val scheduleTemplate = scheduleTemplateService.edit(id, dto)
        val result = dtoBuilder.buildScheduleTemplateDto(scheduleTemplate)
        return restResponse.restSuccess(result)
    }
}