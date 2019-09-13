package com.busticket.booking.service.impl

import com.busticket.booking.entity.Street
import com.busticket.booking.entity.Voyage
import com.busticket.booking.entity.VoyagePart
import com.busticket.booking.lib.assignObject
import com.busticket.booking.repository.region.StreetRepository
import com.busticket.booking.repository.voyage.VoyagePartRepository
import com.busticket.booking.repository.voyage.VoyageRepository
import com.busticket.booking.request.VoyageRequest
import com.busticket.booking.service.interfaces.VoyageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.reflect.KClass

@Service
class VoyageServiceImpl @Autowired constructor(
        voyageRepository: VoyageRepository,
        private val voyagePartRepository: VoyagePartRepository,
        private val streetRepository: StreetRepository
) : VoyageService, BaseService<Voyage, Int>() {
    init {
        primaryRepo = voyageRepository
    }

    override fun getInstanceClass(): KClass<Voyage> {
        return Voyage::class
    }
    override fun create(dto: Any): Voyage {
        dto as VoyageRequest
        val voyage = assignObject(Voyage(), dto)

        val setStreetIds = mutableSetOf<Int>()
        dto.voyagePartRequests.forEach {
            setStreetIds.add(it.fromId)
            setStreetIds.add(it.toId)
        }
        val streets = mapStreetById(streetRepository.findStreetsByIdIn(setStreetIds.toList()))
        val parts = dto.voyagePartRequests.mapIndexed { index, part ->

            assignObject(VoyagePart(
                    voyage = voyage,
                    orderNumber = index + 1,
                    from = streets[part.fromId],
                    to = streets[part.toId]
            ),
                    part)
        }.toSet()

        voyage.voyageParts = parts
        return primaryRepo.save(voyage)
    }

    private fun mapStreetById(streets: List<Street>): Map<Int, Street> {
        val result = mutableMapOf<Int, Street>()
        streets.forEach {
            result[it.id!!] = it
        }
        return result
    }
}