package com.busticket.booking.repository.voyage

import com.busticket.booking.entity.Voyage
import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface VoyageRepository: BaseRepository<Voyage, Int> {
    @Query("select distinct v from Voyage v left join fetch v.voyageParts p left join fetch v.scheduleTemplates vp where v.status = :status")
    fun getAllAndJoin(@Param("status") status: Int = CommonStatus.ACTIVE.value): List<Voyage>

    @Query("select distinct v from Voyage v left join fetch v.voyageParts where v.id = :id and v.status = 1")
    fun getById(@Param("id") id: Int): Optional<Voyage>
}