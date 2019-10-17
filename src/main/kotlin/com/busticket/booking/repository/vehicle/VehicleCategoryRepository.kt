package com.busticket.booking.repository.vehicle

import com.busticket.booking.entity.VehicleCategory
import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface VehicleCategoryRepository: BaseRepository<VehicleCategory, Int> {
    @Query("select distinct vc from VehicleCategory vc join fetch vc.vehicles where vc.id = :id and vc.status = :status")
    fun getByIdAndJoin(@Param("id") id: Int, @Param("status") status: Int = CommonStatus.ACTIVE.value): Optional<VehicleCategory>
}