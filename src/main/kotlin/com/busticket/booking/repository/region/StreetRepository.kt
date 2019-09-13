package com.busticket.booking.repository.region

import com.busticket.booking.entity.Street
import com.busticket.booking.repository.BaseRepository

interface StreetRepository: BaseRepository<Street, Int> {
    fun findStreetsByIdIn(ids: List<Int>): List<Street>
    fun findAllByDistrictId(districtId: Int): List<Street>
}