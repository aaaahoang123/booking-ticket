package com.busticket.booking.repository.voyage

import com.busticket.booking.entity.Voyage
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface VoyageRepository: BaseRepository<Voyage, Int> {
    @Query("select distinct v from Voyage v left join fetch v.voyageParts left join fetch v.scheduleTemplates vp where v.status = 1")
    fun getAllAndJoin(): List<Voyage>
}