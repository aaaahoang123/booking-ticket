package com.busticket.booking.repository.region

import com.busticket.booking.entity.District
import com.busticket.booking.repository.BaseRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DistrictRepository : BaseRepository<District, Int> {
    fun findAllByProvinceId(provinceId: Int): List<District>
}