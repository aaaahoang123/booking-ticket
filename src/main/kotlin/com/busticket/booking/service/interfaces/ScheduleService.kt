package com.busticket.booking.service.interfaces

import com.busticket.booking.entity.Schedule
import com.busticket.booking.entity.User
import com.busticket.booking.request.ScheduleRequest
import com.busticket.booking.service.impl.BaseService
import org.springframework.data.domain.Page

interface ScheduleService : IBaseService<Schedule, Int> {
    fun create(creator: User, dto: ScheduleRequest): Schedule
    fun edit(creator: User, id: Int, dto: ScheduleRequest): Schedule
    fun search(voyageId: Int? = null,
               date: Long? = null,
               driverId: Int? = null,
               scheduleTemplateId: Int? = null,
               vehicleCategoryId: Int? = null,
               status: Int? = null,
               page: Int = 0,
               limit: Int = 20): Page<Schedule>
}