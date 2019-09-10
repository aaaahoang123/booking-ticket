package com.busticket.booking.repository.vehicle

import com.busticket.booking.entity.Vehicle
import com.busticket.booking.repository.BaseRepository
import java.util.*

interface VehicleRepository: BaseRepository<Vehicle, Int> {
    override fun findById(id: Int): Optional<Vehicle> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}