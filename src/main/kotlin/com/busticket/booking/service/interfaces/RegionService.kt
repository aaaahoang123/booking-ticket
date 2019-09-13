package com.busticket.booking.service.interfaces

import com.busticket.booking.dto.DistrictDto
import com.busticket.booking.dto.ProvinceDto
import com.busticket.booking.dto.StreetDto
import com.busticket.booking.entity.District
import com.busticket.booking.entity.Province
import com.busticket.booking.entity.Street

interface RegionService {
    fun getAllProvince(): List<Province>
    fun getAllDistrictByProvinceId(provinceId: Int): List<District>
    fun getAllStreetByDistrictId(districtId: Int): List<Street>
}