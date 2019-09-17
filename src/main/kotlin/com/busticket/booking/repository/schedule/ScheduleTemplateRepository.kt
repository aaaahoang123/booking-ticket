package com.busticket.booking.repository.schedule

import com.busticket.booking.entity.ScheduleTemplate
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface ScheduleTemplateRepository : BaseRepository<ScheduleTemplate, Int> {
    @Query("select distinct s from ScheduleTemplate s join fetch s.voyages where s.status=1 and s.id = ?1")
    fun getById(id: Int): ScheduleTemplate

}