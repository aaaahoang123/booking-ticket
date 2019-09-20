package com.busticket.booking.service.impl

import com.busticket.booking.entity.Schedule
import com.busticket.booking.entity.User
import com.busticket.booking.lib.datetime.startOfDay
import com.busticket.booking.lib.exception.ExecuteException
import com.busticket.booking.repository.fetchRelations
import com.busticket.booking.repository.initSpec
import com.busticket.booking.repository.schedule.*
import com.busticket.booking.repository.user.UserRepository
import com.busticket.booking.repository.vehicle.VehicleCategoryRepository
import com.busticket.booking.repository.vehicle.VehicleRepository
import com.busticket.booking.repository.voyage.VoyageRepository
import com.busticket.booking.request.ScheduleRequest
import com.busticket.booking.service.interfaces.ScheduleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.ArrayList
import kotlin.reflect.KClass

@Service
class ScheduleServiceImpl @Autowired constructor(
        private val scheduleRepo: ScheduleRepository,
        private val scheduleTemplateRepo: ScheduleTemplateRepository,
        private val userRepository: UserRepository,
        private val vehicleRepository: VehicleRepository,
        private val voyageRepository: VoyageRepository,
        private val vehicleCategoryRepo: VehicleCategoryRepository
) : ScheduleService, BaseService<Schedule, Int>() {

    override fun getInstanceClass(): KClass<Schedule> {
        return Schedule::class
    }

    init {
        primaryRepo = scheduleRepo
    }

    override fun create(creator: User, dto: ScheduleRequest): Schedule {
        val schedule = Schedule()

        val template = scheduleTemplateRepo.findById(dto.scheduleTemplateId!!).orElseThrow { ExecuteException("not_found") }
        val vehicleCategory = dto.vehicleCategoryId?.let { vehicleCategoryRepo.findById(it) }
        schedule.vehicleCategory = vehicleCategory?.orElseThrow { ExecuteException("not_found") }
        val startOfDay = startOfDay(dto.scheduleDate!!).timeInMillis
        schedule.startTime = startOfDay + template.timeStart!!
        schedule.endTime = startOfDay + template.timeEnd!!
        schedule.scheduleTemplate = template

        schedule.driver = dto.driverId?.let { userRepository.findById(it).orElse(null) }
        schedule.createdBy = creator
        schedule.vehicle = dto.vehicleId?.let { vehicleRepository.findById(it).orElse(null) }

//        schedule.voyage = dto.voyageId?.let { voyageRepository.findById(it).orElse(null) }
        val oVoyage = voyageRepository.findById(dto.voyageId!!)
        schedule.voyage = if (oVoyage.isPresent) oVoyage.get() else throw ExecuteException("Voyage does not exist")

        return scheduleRepo.save(schedule)
    }

    override fun edit(creator: User, id: Int, dto: ScheduleRequest): Schedule {
        val scheduleExist = scheduleRepo.findById(id).orElse(null)
        val template = scheduleTemplateRepo.findById(dto.scheduleTemplateId!!).orElseThrow { ExecuteException("not_found") }
        val vehicleCategory = dto.vehicleCategoryId?.let { vehicleCategoryRepo.findById(it) }
        scheduleExist.vehicleCategory = vehicleCategory?.orElseThrow { ExecuteException("not_found") }
        val startOfDay = startOfDay(dto.scheduleDate!!).timeInMillis
        scheduleExist.startTime = startOfDay + template.timeStart!!
        scheduleExist.endTime = startOfDay + template.timeEnd!!
        scheduleExist.scheduleTemplate = template

        scheduleExist.driver = dto.driverId?.let { userRepository.findById(it).orElse(null) }
        scheduleExist.createdBy = creator
        scheduleExist.vehicle = dto.vehicleId?.let { vehicleRepository.findById(it).orElse(null) }

        val oVoyage = voyageRepository.findById(dto.voyageId!!)
        scheduleExist.voyage = if (oVoyage.isPresent) oVoyage.get() else throw ExecuteException("Voyage does not exist")
        scheduleExist.updatedAt = Calendar.getInstance().timeInMillis
        return scheduleRepo.save(scheduleExist)
    }

    override fun search(voyageId: Int?, date: Long?, driverId: Int?, scheduleTemplateId: Int?, vehicleCategoryId: Int?, status: Int?, page: Int, limit: Int): Page<Schedule> {
        var spec = Specification.where(initSpec<Schedule>())

        spec = voyageId?.let { spec.and(scheduleHasVoyage(it)) } ?: spec
        spec = date?.let { spec.and(scheduleRunInDate(it)) } ?: spec
        spec = driverId?.let { spec.and(scheduleHasDriver(it)) } ?: spec
        spec = scheduleTemplateId?.let { spec.and(scheduleTemplate(it)) } ?: spec
        spec = status?.let { spec.and(scheduleStatus(it)) } ?: spec
        spec = vehicleCategoryId?.let { spec.and(scheduleHasVehicleCategory(it)) } ?: spec

        return scheduleRepo.findAll(spec, PageRequest.of(page, limit))
    }
}