package com.busticket.booking.service.impl

import com.busticket.booking.dto.DistrictDto
import com.busticket.booking.dto.ProvinceDto
import com.busticket.booking.dto.StreetDto
import com.busticket.booking.entity.District
import com.busticket.booking.entity.Province
import com.busticket.booking.entity.Street
import com.busticket.booking.repository.region.DistrictRepository
import com.busticket.booking.repository.region.ProvinceRepository
import com.busticket.booking.repository.region.StreetRepository
import com.busticket.booking.service.interfaces.RegionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RegionServiceImpl @Autowired constructor(
        private val provinceRepository: ProvinceRepository,
        private val districtRepository: DistrictRepository,
        private val streetRepository: StreetRepository
) : RegionService {
    override fun getAllProvince(): List<Province> {
        return provinceRepository.findAll()
    }

    override fun getAllDistrictByProvinceId(provinceId: Int): List<District> {
        return districtRepository.findAllByProvinceId(provinceId)
    }

    override fun getAllStreetByDistrictId(districtId: Int): List<Street> {
        return streetRepository.findAllByDistrictId(districtId)
    }
}