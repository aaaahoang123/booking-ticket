package com.busticket.booking.repository.vehicle

import com.busticket.booking.entity.Vehicle
import com.busticket.booking.enum.status.CommonStatus
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface VehicleRepository: BaseRepository<Vehicle, Int> {
    @Query("select distinct v from Vehicle v where v.vehicleCategoryId = :categoryId and v.status = :status")
    fun getByVehicleCategoryId(@Param("categoryId") categoryId: Int, @Param("status") status: Int = CommonStatus.ACTIVE.value): List<Vehicle>

}