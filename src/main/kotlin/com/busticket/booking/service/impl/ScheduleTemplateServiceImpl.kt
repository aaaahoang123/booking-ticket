package com.busticket.booking.service.impl

import com.busticket.booking.entity.ScheduleTemplate
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.schedule.ScheduleTemplateRepository
import com.busticket.booking.repository.voyage.VoyageRepository
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.service.interfaces.ScheduleTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class ScheduleTemplateServiceImpl @Autowired constructor(
        private val scheduleTemplateRepository: ScheduleTemplateRepository,
        private val voyageRepository: VoyageRepository
) : ScheduleTemplateService, BaseService<ScheduleTemplate, Int>() {

    init {
        this.primaryRepo = scheduleTemplateRepository
    }

    override fun getInstanceClass(): KClass<ScheduleTemplate> {
        return ScheduleTemplate::class
    }

    override fun create(dto: Any): ScheduleTemplate {
        dto as ScheduleTemplateRequest
        val scheduleTemplate = assignObject(ScheduleTemplate(), dto)
        val voyages = dto.listVoyage.map { voyageRepository.getOne(it) }.toSet()
        scheduleTemplate.voyages = voyages
        return primaryRepo.save(scheduleTemplate)
    }
}