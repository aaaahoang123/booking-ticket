package com.busticket.booking.repository.voyage

import com.busticket.booking.entity.Voyage
import com.busticket.booking.entity.VoyagePart
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface VoyagePartRepository : BaseRepository<VoyagePart, Int> {
    @Query("from VoyagePart v where v.voyage = :voyage and v.orderNumber between :partFrom and :partTo")
    fun findByOrderNumberBetween(@Param("voyage") voyage: Voyage, @Param("partFrom") partFrom: Int, @Param("partTo") partTo: Int): List<VoyagePart>
}