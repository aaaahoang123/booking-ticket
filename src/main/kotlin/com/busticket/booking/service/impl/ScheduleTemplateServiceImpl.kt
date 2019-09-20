package com.busticket.booking.service.impl

import com.busticket.booking.entity.ScheduleTemplate
import com.busticket.booking.entity.Voyage
import com.busticket.booking.lib.assignObject
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.fetchRelation
import com.busticket.booking.repository.hasStatus
import com.busticket.booking.repository.schedule.ScheduleTemplateRepository
import com.busticket.booking.repository.voyage.VoyageRepository
import com.busticket.booking.request.ScheduleTemplateRequest
import com.busticket.booking.service.interfaces.ScheduleTemplateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.concurrent.ExecutionException
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
        val voyages = dto.listVoyageIds.map { voyageRepository.getOne(it) }.toSet()
        scheduleTemplate.voyages = voyages
        return primaryRepo.save(scheduleTemplate)
    }

    override fun edit(id: Int, dto: Any): ScheduleTemplate {
        dto as ScheduleTemplateRequest
        val scheduleTemplateExist = scheduleTemplateRepository.findById(id)
        if (!scheduleTemplateExist.isPresent) {
            throw ExecuteException("not_found")
        }
        val scheduleTemplate = assignObject(scheduleTemplateExist.get(), dto)
        val voyages = voyageRepository.findByIdIsIn(dto.listVoyageIds)
        scheduleTemplate.voyages = voyages.toMutableSet()
        return primaryRepo.save(scheduleTemplate)
    }

    override fun singleById(id: Int): ScheduleTemplate {
//        val spec = Specification.where(fetchRelation<ScheduleTemplate, Voyage>("voyages"))
        return scheduleTemplateRepository.getById(id)
//        return scheduleTemplateRepository.findAll(spec).first()
    }

    override fun findAllActiveItems(): List<ScheduleTemplate> {
        return scheduleTemplateRepository.findAll(
                Specification.where(hasStatus<ScheduleTemplate>())
                        .and(fetchRelation<ScheduleTemplate, Voyage>("voyages"))
        )
    }
}