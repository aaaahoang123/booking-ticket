package com.busticket.booking.repository.schedule

import com.busticket.booking.entity.Schedule
import com.busticket.booking.lib.datetime.endOfDay
import com.busticket.booking.lib.datetime.startOfDay
import org.springframework.data.jpa.domain.Specification

fun scheduleHasVoyage(voyageId: Int): Specification<Schedule> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("voyageId"), voyageId)
    }
}

fun scheduleRunInDate(date: Long): Specification<Schedule> {
    return Specification { root, query, criteriaBuilder ->
        val startOfDay = startOfDay(date)
        val endOfDay = endOfDay(date)
        criteriaBuilder.and(
                criteriaBuilder.ge(root["startTime"], startOfDay.timeInMillis),
                criteriaBuilder.lt(root["endTime"], endOfDay.timeInMillis)
        )
    }
}

fun scheduleHasDriver(driverId: Int): Specification<Schedule> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("driver"), driverId)
    }
}

fun scheduleTemplate(scheduleTemplateId: Int): Specification<Schedule> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("scheduleTemplateId"), scheduleTemplateId)
    }
}

fun scheduleStatus(status: Int): Specification<Schedule> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("status"), status)
    }
}

fun scheduleHasVehicleCategory(vehicleCategory: Int): Specification<Schedule> {
    return Specification { root, query, criteriaBuilder ->
        criteriaBuilder.equal(root.get<Any>("vehicleCategoryId"), vehicleCategory)
    }
}