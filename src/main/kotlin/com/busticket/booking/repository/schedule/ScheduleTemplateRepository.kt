package com.busticket.booking.repository.schedule

import com.busticket.booking.entity.ScheduleTemplate
import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ScheduleTemplateRepository : BaseRepository<ScheduleTemplate, Int> {
    @Query("select distinct s from ScheduleTemplate s join fetch s.voyages where s.status= :status and s.id = :id")
    fun getById(@Param("id") id: Int, @Param("status") status: Int = CommonStatus.ACTIVE.value): ScheduleTemplate

}