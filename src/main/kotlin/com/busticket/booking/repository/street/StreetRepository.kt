package com.busticket.booking.repository.street

import com.busticket.booking.entity.Street
import com.busticket.booking.repository.BaseRepository

interface StreetRepository: BaseRepository<Street, Int> {
    fun findStreetsByIdIn(ids: List<Int>): List<Street>
}